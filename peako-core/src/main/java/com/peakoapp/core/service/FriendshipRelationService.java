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
}
