package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.model.dto.FriendRequestPayload;
import java.io.Serializable;
import java.util.Date;

/**
 * The {@code FriendRequestSummary} class is a summary of the friend request including the
 * information about the users and the request.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendRequestSummary implements Serializable {
    private static final long serialVersionUID = -8719544459166030370L;

    /**
     * The friend request id.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    /**
     * The full profile details of the sender.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProfileDetails sender;

    /**
     * The full profile details of the receiver.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProfileDetails receiver;

    /**
     * The approval state of the friend request.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean approved;

    /**
     * The denial state of the friend request.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean denied;

    /**
     * The expiration date of the friend request.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date expiredAt;

    public FriendRequestSummary() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public FriendRequestSummary(FriendRequestPayload payload) {
        this.id = payload.getId();
        this.approved = payload.getApproved();
        this.denied = payload.getDenied();
        this.expiredAt = payload.getExpiredAt();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public FriendRequestSummary(Long id, ProfileDetails sender, ProfileDetails receiver,
                                Boolean approved, Boolean denied, Date expiredAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.approved = approved;
        this.denied = denied;
        this.expiredAt = expiredAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileDetails getSender() {
        return sender;
    }

    public void setSender(ProfileDetails sender) {
        this.sender = sender;
    }

    public ProfileDetails getReceiver() {
        return receiver;
    }

    public void setReceiver(ProfileDetails receiver) {
        this.receiver = receiver;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }
}
