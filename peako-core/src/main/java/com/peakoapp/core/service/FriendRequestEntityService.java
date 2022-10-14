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
     * Obtains a list of friend requests sent by the user with the given id to other users.
     *
     * @param senderId The id of the user who sends friend requests to others.
     * @return A list of friend requests sent.
     */
    List<FriendRequestPayload> getRequestsSentById(Long senderId);

    /**
     * Obtains a list of friend requests received by the user with the given id.
     *
     * @param receiverId The id of the user who receives friend requests to others.
     * @return A list of friend requests received.
     */
    List<FriendRequestPayload> getRequestsReceivedById(Long receiverId);
}
