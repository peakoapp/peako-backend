package com.peakoapp.core.repository;

import com.peakoapp.core.model.entity.FriendshipRelation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code FriendshipRelationRepository} defines interfaces of CRUD operations on
 * {@link FriendshipRelation}.
 *
 * @version 0.1.0
 */
public interface FriendshipRelationRepository extends CrudRepository<FriendshipRelation, Long> {
    /**
     * Checks if the given two users are friends with each other.
     *
     * @param id The id of one of the users.
     * @param userId The id of the other user.
     * @return True if they are friends with each other; otherwise, false.
     */
    @Query(
            value = "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END"
                    + " FROM FriendshipRelation r WHERE (r.userId = :id AND r.friendId = :userId)"
                    + " OR (r.userId = :userId AND r.friendId = :id)"
    )
    boolean isFriend(@Param(value = "id") Long id, @Param(value = "userId") Long userId);

    /**
     * Deletes the friendship relation between the given two users.
     *
     * @param id The id of one of the users.
     * @param friendId The id of the other user.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
            value = "DELETE FROM FriendshipRelation r"
                    + " WHERE (r.userId = :id AND r.friendId = :friendId) OR"
                    + " (r.userId = :friendId AND r.friendId = :id)"
    )
    void deleteByRelation(@Param(value = "id") Long id, @Param(value = "friendId") Long friendId);
}
