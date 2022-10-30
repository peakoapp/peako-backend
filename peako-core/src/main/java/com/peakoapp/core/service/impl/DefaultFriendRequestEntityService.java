package com.peakoapp.core.service.impl;

import com.peakoapp.core.model.dto.FriendRequestPayload;
import com.peakoapp.core.model.entity.FriendRequestEntity;
import com.peakoapp.core.repository.FriendRequestEntityRepository;
import com.peakoapp.core.service.FriendRequestEntityService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private static final int PAGE_SIZE = 20;

    private final FriendRequestEntityRepository requestRepository;

    public DefaultFriendRequestEntityService(FriendRequestEntityRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FriendRequestPayload> getById(Long id) {
        return requestRepository.findById(id).map(FriendRequestPayload::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestPayload> listRequestsOf(Long userId, int page) {
        List<FriendRequestPayload> payloads = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending());
        requestRepository.findRequestsOf(userId, pageable)
                .forEach(entity -> payloads.add(new FriendRequestPayload(entity)));
        return payloads;
    }

    @Override
    public Optional<FriendRequestPayload> create(FriendRequestPayload payload) {
        // callers take care of the case where two users are already friends
        Long sid = payload.getSenderId();
        Long rid = payload.getReceiverId();
        List<FriendRequestEntity> requests = requestRepository.findRequestsBetween(sid, rid);
        for (FriendRequestEntity request : requests) {
            if (isRequestActive(request)) {
                return Optional.empty();
            }
        }
        return Optional.of(new FriendRequestPayload(requestRepository.save(payload.as())));
    }

    @Override
    public Optional<FriendRequestPayload> updateById(FriendRequestPayload payload) {
        return requestRepository.findById(payload.getId()).map(entity -> {
            if (isRequestActive(entity)) {
                entity.setApproved(payload.getApproved());
                entity.setDenied(payload.getDenied());
                return new FriendRequestPayload(requestRepository.save(entity));
            } else {
                return null;
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    private boolean isRequestActive(FriendRequestEntity entity) {
        return !entity.getDenied() && !entity.getApproved()
                && entity.getExpiredAt().after(new Date());
    }
}
