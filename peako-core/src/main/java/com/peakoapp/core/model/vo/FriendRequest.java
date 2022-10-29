package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.model.dto.FriendRequestPayload;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * The {@code FriendRequest} class is a subset of {@link FriendRequestPayload} used to create a new
 * friend request between the given users.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendRequest implements Details<FriendRequestPayload>, Serializable {
    private static final long serialVersionUID = 4521710379840122978L;

    /**
     * The id of the user who sends the friend request.
     * This field should be set explicitly instead of relying on the user-passed JSON value through
     * the API calls.
     */
    @Null
    private Long senderId;

    /**
     * The id of the user who receives the friend request.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long receiverId;

    public FriendRequest() {
    }

    public FriendRequest(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public FriendRequestPayload as() {
        FriendRequestPayload payload = new FriendRequestPayload();
        payload.setSenderId(senderId);
        payload.setReceiverId(receiverId);
        return payload;
    }
}
