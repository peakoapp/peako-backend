package com.peakoapp.service.impl;

import com.peakoapp.core.exception.AlreadyFriendException;
import com.peakoapp.core.exception.BadParameterException;
import com.peakoapp.core.exception.ForbiddenAccessException;
import com.peakoapp.core.model.dto.FriendRequestPayload;
import com.peakoapp.core.model.vo.FriendRequest;
import com.peakoapp.core.model.vo.FriendRequestStatus;
import com.peakoapp.core.model.vo.FriendRequestSummary;
import com.peakoapp.core.model.vo.ProfileDetails;
import com.peakoapp.core.service.FriendRequestEntityService;
import com.peakoapp.core.service.FriendshipRelationService;
import com.peakoapp.service.FriendService;
import com.peakoapp.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code DefaultFriendService} class is the default implementation of {@link FriendService} and
 * provides basic friend-related functionalities.
 *
 * @version 0.1.0
 */
@Transactional(rollbackFor = Exception.class)
@Primary
@Service(value = "defaultFriendService")
public class DefaultFriendService implements FriendService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFriendService.class);

    private final UserService userService;
    private final FriendRequestEntityService friendRequestService;
    private final FriendshipRelationService friendshipRelationService;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public DefaultFriendService(UserService userService,
                                FriendRequestEntityService friendRequestService,
                                FriendshipRelationService friendshipRelationService) {
        this.userService = userService;
        this.friendRequestService = friendRequestService;
        this.friendshipRelationService = friendshipRelationService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfileDetails> listFriendProfiles(Long userId) {
        logger.debug("Friend::User::{}: Retrieving friends' profiles", userId);
        List<ProfileDetails> list = new ArrayList<>();
        friendshipRelationService.getFriendsById(userId)
                .forEach(payload -> list.add(new ProfileDetails(payload)));
        logger.debug("Friend::User::{}: Found {} friends", userId, list.size());
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestSummary> listFriendRequestsOf(Long userId, int page) {
        logger.debug("Friend::User::{}: Retrieving associated friend requests", userId);
        List<FriendRequestSummary> list = new ArrayList<>();
        friendRequestService.listRequestsOf(userId, page).forEach(payload -> {
            FriendRequestSummary summary = new FriendRequestSummary(payload);
            userService.getProfileById(payload.getSenderId()).ifPresent(summary::setSender);
            userService.getProfileById(payload.getReceiverId()).ifPresent(summary::setReceiver);
            // if for some reason, one user cannot be found, ignore this friend request
            // very likely, that user deletes their account
            if (summary.getSender() != null && summary.getReceiver() != null) {
                list.add(summary);
            }
        });
        logger.debug("Friend::User::{}: Found {} friend requests in page #{}", userId, list.size(),
                page);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestSummary> listFriendRequestsSent(Long userId, int page) {
        logger.debug("Friend::User::{}: Retrieving friend requests sent", userId);
        List<FriendRequestSummary> list = new ArrayList<>();
        friendRequestService.listRequestsSent(userId, page).forEach(payload -> {
            FriendRequestSummary summary = new FriendRequestSummary(payload);
            userService.getProfileById(payload.getReceiverId()).ifPresent(summary::setReceiver);
            if (summary.getReceiver() != null) {
                list.add(summary);
            }
        });
        logger.debug("Friend::User::{}: Found {} friend requests sent in page #{}", userId,
                list.size(), page);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FriendRequestSummary> listFriendRequestsReceived(Long userId, int page) {
        logger.debug("Friend::User::{}: Retrieving friend requests received", userId);
        List<FriendRequestSummary> list = new ArrayList<>();
        friendRequestService.listRequestsReceived(userId, page).forEach(payload -> {
            FriendRequestSummary summary = new FriendRequestSummary(payload);
            userService.getProfileById(payload.getSenderId()).ifPresent(summary::setSender);
            if (summary.getSender() != null) {
                list.add(summary);
            }
        });
        logger.debug("Friend::User::{}: Found {} friend requests received in page #{}", userId,
                list.size(), page);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFriend(Long userId1, Long userId2) {
        logger.debug("Friend: Checking if {} and {} are friends", userId1, userId2);
        return friendshipRelationService.hasRelation(userId1, userId2);
    }

    @Override
    public boolean sendFriendRequest(FriendRequest request) {
        if (request.getSenderId().equals(request.getReceiverId())) {
            logger.debug("Friend: Invalid friend request sent to oneself");
            throw new BadParameterException("Invalid friend request sent to oneself.");
        }
        if (isFriend(request.getSenderId(), request.getReceiverId())) {
            logger.debug("Friend: Invalid friend request sent to a friend");
            throw new AlreadyFriendException("Invalid friend request sent to a friend.");
        }
        logger.debug("Friend: Creating a new friend request");
        return friendRequestService.create(request.as()).isPresent();
    }

    @Override
    public boolean updateFriendRequest(FriendRequestStatus requestStatus, Long receiverId) {
        Optional<FriendRequestPayload> opt = friendRequestService.getById(requestStatus.getId());
        if (opt.isEmpty()) {
            logger.debug("Friend::{}: Not found", requestStatus.getId());
            throw new BadParameterException("The friend request id does not exist.");
        }
        if (!opt.get().getReceiverId().equals(receiverId)) {
            logger.debug("Friend::{}: Receiver is not {}", requestStatus.getId(), receiverId);
            throw new ForbiddenAccessException("The friend request receiver id doesn't match.");
        }
        logger.debug("Friend::{}: Updating the friend request", requestStatus.getId());
        return friendRequestService.updateById(requestStatus.as()).map(load -> {
            if (load.getApproved()) {
                logger.debug("Friend::{}: Approved", requestStatus.getId());
                friendshipRelationService.createRelation(load.getSenderId(), load.getReceiverId());
            } else {
                logger.debug("Friend::{}: Denied", requestStatus.getId());
            }
            return true;
        }).orElse(false);
    }

    @Override
    public void deleteFriendById(Long userId, Long friendId) {
        logger.debug("Friend: Deleting the friendship between {} and {}", userId, friendId);
        friendshipRelationService.deleteRelation(userId, friendId);
    }
}
