package com.peakoapp.core.service.impl;

import com.peakoapp.core.model.dto.FriendRequestPayload;
import com.peakoapp.core.model.entity.FriendRequestEntity;
import com.peakoapp.core.repository.FriendRequestEntityRepository;
import com.peakoapp.core.service.FriendRequestEntityService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code DefaultFriendRequestEntityService} class is the default implementation of
 * {@link FriendRequestEntityService} that provides basic CRUD operations on
 * {@link FriendRequestEntity}.
 *
 * @version 0.1.0
 */
@Transactional(rollbackFor = Exception.class)
@Primary
@Service(value = "defaultFriendRequestEntityService")
public class DefaultFriendRequestEntityService implements FriendRequestEntityService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFriendRequestEntityService.class);
    private static final int PAGE_SIZE = 20;

    private final FriendRequestEntityRepository requestRepository;

    public DefaultFriendRequestEntityService(FriendRequestEntityRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FriendRequestPayload> getById(Long id) {
        logger.debug("DATABASE::FriendRequestEntity::{}: Retrieving", id);
        return requestRepository.findById(id).map(FriendRequestPayload::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestPayload> listRequestsOf(Long userId, int page) {
        logger.debug("DATABASE::FriendRequestEntity: Retrieving friend requests associated with"
                + " the user {}", userId);
        List<FriendRequestPayload> payloads = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending());
        requestRepository.findRequestsOf(userId, pageable)
                .forEach(entity -> payloads.add(new FriendRequestPayload(entity)));
        logger.debug("DATABASE::FriendRequestEntity: Found {} requests in page #{}",
                payloads.size(), page);
        return payloads;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestPayload> listRequestsSent(Long senderId, int page) {
        logger.debug("DATABASE::FriendRequestEntity: Retrieving friend requests sent by the user"
                + " {}", senderId);
        List<FriendRequestPayload> payloads = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending());
        requestRepository.findRequestsSent(senderId, pageable)
                .forEach(entity -> payloads.add(new FriendRequestPayload(entity)));
        logger.debug("DATABASE::FriendRequestEntity: Found {} requests in page #{}",
                payloads.size(), page);
        return payloads;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestPayload> listRequestsReceived(Long receiverId, int page) {
        logger.debug("DATABASE::FriendRequestEntity: Retrieving friend requests received by the"
                + " user {}", receiverId);
        List<FriendRequestPayload> payloads = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending());
        requestRepository.findRequestsReceived(receiverId, pageable)
                .forEach(entity -> payloads.add(new FriendRequestPayload(entity)));
        logger.debug("DATABASE::FriendRequestEntity: Found {} requests in page #{}",
                payloads.size(), page);
        return payloads;
    }

    @Override
    public Optional<FriendRequestPayload> create(FriendRequestPayload payload) {
        // callers take care of the case where two users are already friends
        Long sid = payload.getSenderId();
        Long rid = payload.getReceiverId();
        logger.debug("DATABASE::FriendRequestEntity: New friend request from {} to {}", sid, rid);
        List<FriendRequestEntity> requests = requestRepository.findRequestsBetween(sid, rid);
        for (FriendRequestEntity request : requests) {
            if (isRequestActive(request)) {
                logger.debug("DATABASE::FriendRequestEntity::{}: Found an active friend request",
                        request.getId());
                return Optional.empty();
            }
        }
        logger.debug("DATABASE::FriendRequestEntity: Creating the new friend request");
        return Optional.of(new FriendRequestPayload(requestRepository.save(payload.as())));
    }

    @Override
    public Optional<FriendRequestPayload> updateById(FriendRequestPayload payload) {
        Long requestId = payload.getId();
        return requestRepository.findById(requestId).map(entity -> {
            logger.debug("DATABASE::FriendRequestEntity::{}: Found", requestId);
            if (isRequestActive(entity)) {
                logger.debug("DATABASE::FriendRequestEntity::{}: Active", entity.getId());
                logger.debug("DATABASE::FriendRequestEntity::{}: Updating the request", requestId);
                entity.setApproved(payload.getApproved());
                entity.setDenied(payload.getDenied());
                return new FriendRequestPayload(requestRepository.save(entity));
            } else {
                logger.debug("DATABASE::FriendRequestEntity::{}: Inactive", entity.getId());
                return null;
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("DATABASE::FriendRequestEntity::{}: Deleting a friend request (should not come"
                + " here)", id);
        requestRepository.deleteById(id);
    }

    private boolean isRequestActive(FriendRequestEntity entity) {
        return !entity.getDenied() && !entity.getApproved()
                && entity.getExpiredAt().after(new Date());
    }
}
