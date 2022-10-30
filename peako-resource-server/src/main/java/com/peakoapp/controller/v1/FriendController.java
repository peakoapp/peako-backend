package com.peakoapp.controller.v1;

import com.peakoapp.core.model.dto.AuthPayload;
import com.peakoapp.core.model.vo.FriendRequest;
import com.peakoapp.core.model.vo.FriendRequestStatus;
import com.peakoapp.core.utils.R;
import com.peakoapp.enums.FriendRequestFilter;
import com.peakoapp.service.FriendService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code FriendController} class provides the endpoints for friend services.
 *
 * @version 0.1.0
 */
@RestController
@RequestMapping(value = "/v1/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * Gets a list of friends' profiles.
     */
    @GetMapping(value = "/profiles")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> getFriendProfiles(Authentication auth) {
        AuthPayload payload = (AuthPayload) auth.getPrincipal();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("friends", friendService.listFriendProfiles(payload.getId()));
        return R.ok(resultMap);
    }

    /**
     * Gets a list of friend requests.
     */
    @GetMapping(value = "/requests")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> getFriendRequests(@RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "type") FriendRequestFilter type,
                                  Authentication auth) {
        AuthPayload payload = (AuthPayload) auth.getPrincipal();
        Long userId = payload.getId();
        Map<String, Object> map = new HashMap<>();
        switch (type) {
            case ALL:
                map.put("requests", friendService.listFriendRequestsOf(userId, page));
                break;
            case SENT:
                map.put("requests", friendService.listFriendRequestsSent(userId, page));
                break;
            case RECEIVED:
                map.put("requests", friendService.listFriendRequestsReceived(userId, page));
                break;
            default:
                break;
        }
        return R.ok(map);
    }

    /**
     * Sends a friend request to a non-friend user.
     */
    @PostMapping(value = "/request")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> sendFriendRequest(@Validated @RequestBody FriendRequest request,
                                  Authentication auth) {
        AuthPayload payload = (AuthPayload) auth.getPrincipal();
        request.setSenderId(payload.getId());
        return friendService.sendFriendRequest(request) ? R.ok() : R.bad();
    }

    /**
     * Updates a friend request as the receiver.
     */
    @PutMapping(value = "/request")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> updateFriendRequest(@Validated @RequestBody FriendRequestStatus friendRequestStatus,
                                    Authentication auth) {
        AuthPayload payload = (AuthPayload) auth.getPrincipal();
        return friendService.updateFriendRequest(friendRequestStatus, payload.getId())
                ? R.ok() : R.bad();
    }

    /**
     * Deletes the friendship with the given user.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> deleteFriend(@PathVariable(value = "id") Long friendId, Authentication auth) {
        AuthPayload payload = (AuthPayload) auth.getPrincipal();
        friendService.deleteFriendById(payload.getId(), friendId);
        return R.ok();
    }
}
