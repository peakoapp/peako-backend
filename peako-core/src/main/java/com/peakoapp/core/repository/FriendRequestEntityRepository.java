package com.peakoapp.core.repository;

import com.peakoapp.core.model.entity.FriendRequestEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code FriendRequestEntityRepository} defines interfaces of CRUD operations on
 * {@link FriendRequestEntity}.
 *
 * @version 0.1.0
 */
public interface FriendRequestEntityRepository extends CrudRepository<FriendRequestEntity, Long> {
    /**
     * Retrieves a paginated list of friend requests associated with the user with the given id.
     *
     * @param userId The id of the user.
     * @param pageable The pagination requirements.
     * @return  A list of friend requests.
     */
    @Query(
            value = "SELECT e FROM FriendRequestEntity e"
                    + " WHERE e.senderId = :userId OR e.receiverId = :userId",
            countQuery = "SELECT COUNT(e) FROM FriendRequestEntity e"
                    + " WHERE e.senderId = :userId OR e.receiverId = :userId"
    )
    Page<FriendRequestEntity> findRequestsOf(@Param(value = "userId") Long userId, Pageable pageable);

    /**
     * Retrieves a paginated list of friend requests sent by the user with the given id.
     *
     * @param senderId The id of the sender.
     * @param pageable The pagination requirements.
     * @return  A list of friend requests.
     */
    @Query(
            value = "SELECT e FROM FriendRequestEntity e"
                    + " WHERE e.senderId = :senderId",
            countQuery = "SELECT COUNT(e) FROM FriendRequestEntity e"
                    + " WHERE e.senderId = :senderId"
    )
    Page<FriendRequestEntity> findRequestsSent(@Param(value = "senderId") Long senderId, Pageable pageable);

    /**
     * Retrieves a paginated list of friend requests received by the user with the given id.
     *
     * @param receiverId The id of the receiver.
     * @param pageable The pagination requirements.
     * @return  A list of friend requests.
     */
    @Query(
            value = "SELECT e FROM FriendRequestEntity e"
                    + " WHERE e.receiverId = :receiverId",
            countQuery = "SELECT COUNT(e) FROM FriendRequestEntity e"
                    + " WHERE e.receiverId = :receiverId"
    )
    Page<FriendRequestEntity> findRequestsReceived(@Param(value = "receiverId") Long receiverId, Pageable pageable);

    /**
     * Retrieves the full list of friend requests between the given sender and the given receiver
     * sorted by the request time (create time) in the descending order (the latest requests first).
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
    List<FriendRequestEntity> findRequestsBetween(@Param(value = "senderId") Long senderId,
                                                  @Param(value = "receiverId") Long receiverId);
}
