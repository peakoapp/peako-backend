package com.peakoapp.core.service;

import com.peakoapp.core.model.dto.FriendRequestPayload;
import java.util.List;

/**
 * The {@code FriendRequestEntityService} provides basic methods for manipulating
 * {@link com.peakoapp.core.model.entity.FriendRequestEntity}.
 *
 * @version 0.1.0
 */
public interface FriendRequestEntityService extends EntityService<FriendRequestPayload> {
    /**
     * Retrieves a paginated list of friend requests associated with the user with the given id.
     *
     * @param userId The id of the user.
     * @param page The page offset.
     * @return A list of friend requests.
     */
    List<FriendRequestPayload> listRequestsOf(Long userId, int page);

    /**
     * Retrieves a paginated list of friend requests sent by the user with the given id.
     *
     * @param senderId The id of the sender.
     * @param page The page offset.
     * @return A list of friend requests.
     */
    List<FriendRequestPayload> listRequestsSent(Long senderId, int page);

    /**
     * Retrieves a paginated list of friend requests received by the user with the given id.
     *
     * @param receiverId The id of the receiver.
     * @param page The page offset.
     * @return A list of friend requests.
     */
    List<FriendRequestPayload> listRequestsReceived(Long receiverId, int page);
}
