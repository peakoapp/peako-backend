package com.peakoapp.core.service.impl;

import com.peakoapp.core.model.dto.UserPayload;
import com.peakoapp.core.model.entity.FriendshipRelation;
import com.peakoapp.core.model.entity.UserEntity;
import com.peakoapp.core.repository.FriendshipRelationRepository;
import com.peakoapp.core.repository.UserEntityRepository;
import com.peakoapp.core.service.FriendshipRelationService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code DefaultFriendshipRelationService} class is the default implementation of
 * {@link FriendshipRelationService} that provides basic CRUD operations on
 * {@link FriendshipRelation}.
 *
 * @version 0.1.0
 */
@Transactional(rollbackFor = Exception.class)
@Primary
@Service(value = "defaultFriendshipRelationService")
public class DefaultFriendshipRelationService implements FriendshipRelationService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFriendshipRelationService.class);

    private final UserEntityRepository userEntityRepository;
    private final FriendshipRelationRepository friendshipRelationRepository;

    public DefaultFriendshipRelationService(UserEntityRepository userEntityRepository,
                                            FriendshipRelationRepository friendshipRelationRepository) {
        this.userEntityRepository = userEntityRepository;
        this.friendshipRelationRepository = friendshipRelationRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UserPayload> getFriendsById(Long id) {
        logger.debug("DATABASE::UserEntity::{}: Retrieving the friend list", id);
        return userEntityRepository.findById(id).map(entity -> {
            logger.debug("DATABASE::UserEntity::{}: Found", entity.getId());
            Set<UserPayload> payloads = new HashSet<>();
            Set<UserEntity> friends = new HashSet<>();
            friends.addAll(entity.getFriends());
            friends.addAll(entity.getFriendOf());
            friends.forEach(e -> payloads.add(new UserPayload(e)));
            return payloads;
        }).orElse(Collections.emptySet());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasRelation(Long id1, Long id2) {
        logger.debug("DATABASE::FriendshipRelation({}, {}): Checking the friendship", id1, id2);
        return friendshipRelationRepository.isFriend(id1, id2);
    }

    @Override
    public void createRelation(Long id, Long userId) {
        logger.debug("DATABASE::FriendshipRelation({}, {}): Creating a relation", id, userId);
        friendshipRelationRepository.save(new FriendshipRelation(null, id, userId, null));
    }

    @Override
    public void deleteRelation(Long id, Long friendId) {
        logger.debug("DATABASE::FriendshipRelation({}, {}): deleting a relation", id, friendId);
        friendshipRelationRepository.deleteByRelation(id, friendId);
    }
}
