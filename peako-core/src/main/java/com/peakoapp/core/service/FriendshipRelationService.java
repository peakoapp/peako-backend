package com.peakoapp.core.service;

import com.peakoapp.core.model.dto.UserPayload;
import java.util.Set;

/**
 * The {@code FriendshipRelationService} provides basic methods for manipulating
 * {@link com.peakoapp.core.model.entity.FriendshipRelation friendship relations} and fetches
 * friend lists represented by a set of {@link UserPayload}s.
 *
 * @version 0.1.0
 */
public interface FriendshipRelationService extends DualRelationService {
    /**
     * Obtains the friend list of the user with the given id.
     *
     * @param id The id of the user who requests their friend list.
     * @return A collection of their friends' information.
     */
    Set<UserPayload> getFriendsById(Long id);

    /**
     * Checks if the given two users are friends with each other.
     *
     * @param id The id of one of the users.
     * @param userId The id of the other user.
     * @return True if they are friends with each other; otherwise, false.
     */
    boolean isFriend(Long id, Long userId);
}
