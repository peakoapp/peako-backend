package com.peakoapp.service;

import com.peakoapp.core.model.vo.FriendRequest;
import com.peakoapp.core.model.vo.FriendRequestStatus;
import com.peakoapp.core.model.vo.FriendRequestSummary;
import com.peakoapp.core.model.vo.ProfileDetails;
import java.util.List;

/**
 * The {@code FriendService} interface defines the methods that handle friend-related CRUD
 * functionalities.
 *
 * @version 0.1.0
 */
public interface FriendService {
    /**
     * Retrieves all the given user's friends' profiles.
     *
     * @param userId The id of the user who has these friends.
     * @return A list of friends' profiles.
     */
    List<ProfileDetails> listFriendProfiles(Long userId);

    /**
     * Retrieves a page (20 records by default) of friend requests associated with the given user.
     *
     * @param userId The id of the user who's associated with these friend requests.
     * @param page The page number.
     * @return A list of friend request summaries.
     */
    List<FriendRequestSummary> listFriendRequestsOf(Long userId, int page);

    /**
     * Retrieves a page (20 records by default) of friend requests sent by the given user.
     *
     * @param userId The id of the user who sends these friend requests.
     * @param page The page number.
     * @return A list of friend request summaries.
     */
    List<FriendRequestSummary> listFriendRequestsSent(Long userId, int page);

    /**
     * Retrieves a page (20 records by default) of friend requests received by the given user.
     *
     * @param userId The id of the user who receives these friend requests.
     * @param page The page number.
     * @return A list of friend request summaries.
     */
    List<FriendRequestSummary> listFriendRequestsReceived(Long userId, int page);

    /**
     * Checks if the given two users are friends.
     *
     * @param userId1 The id of one of the users.
     * @param userId2 The id of the other user.
     * @return True if they are friends, false otherwise.
     */
    boolean isFriend(Long userId1, Long userId2);

    /**
     * Creates a new friend request between the given users.
     *
     * @param request The friend request information.
     * @return True if successful, false otherwise.
     */
    boolean sendFriendRequest(FriendRequest request);

    /**
     * Updates the status of the friend request (approved or denied).
     *
     * @param requestStatus The new status of the friend request.
     * @param receiverId The id of the user who performs the action.
     * @return True if successful, false otherwise.
     */
    boolean updateFriendRequest(FriendRequestStatus requestStatus, Long receiverId);

    /**
     * Deletes the friendship between the given users.
     *
     * @param userId The id of one of the users.
     * @param friendId The id of the other user.
     */
    void deleteFriendById(Long userId, Long friendId);
}
