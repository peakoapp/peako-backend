package com.peakoapp.core.repository;

import com.peakoapp.core.model.entity.FriendRequestEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code FriendRequestEntityRepository} defines interfaces of CRUD operations on
 * {@link FriendRequestEntity}.
 *
 * @version 0.1.0
 */
public interface FriendRequestEntityRepository extends CrudRepository<FriendRequestEntity, Long> {
    /**
     * Obtains a list of friend requests between the given sender and the given receiver sorted by
     * the request time (create time) in the descending order (the latest requests first).
     *
     * @param senderId The id of the user who sends the friend requests to the receiver.
     * @param receiverId The id of the user who receives the friend requests.
     * @return A list of friend requests.
     */
    @Query(
            value = "SELECT e FROM FriendRequestEntity e"
                    + " WHERE e.senderId = :senderId AND e.receiverId = :receiverId"
                    + " ORDER BY e.createTime DESC"
    )
    List<FriendRequestEntity> findLatestBetween(Long senderId, Long receiverId);

    /**
     * Obtains a list of friend requests sent by the user with the given id to other users.
     *
     * @param senderId The id of the user who sends friend requests to others.
     * @return A list of friend requests sent.
     */
    List<FriendRequestEntity> findBySenderId(Long senderId);

    /**
     * Obtains a list of friend requests received by the user with the given id.
     *
     * @param receiverId The id of the user who receives friend requests to others.
     * @return A list of friend requests received.
     */
    List<FriendRequestEntity> findByReceiverId(Long receiverId);
}
