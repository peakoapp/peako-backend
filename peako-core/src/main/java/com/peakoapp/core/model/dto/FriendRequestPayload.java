package com.peakoapp.core.model.dto;

import com.peakoapp.core.model.entity.FriendRequestEntity;
import java.io.Serializable;
import java.util.Date;

/**
 * The {@code FriendRequestPayload} class is an exact copy of {@link FriendRequestEntity} for the
 * purpose of data transfer.
 *
 * @version 0.1.0
 */
public class FriendRequestPayload implements Payload<FriendRequestEntity>, Serializable {
    private static final long serialVersionUID = 1139616544589163623L;

    private Long id;
    private Long senderId;
    private Long receiverId;
    private Boolean approved;
    private Boolean denied;
    private Date expiredAt;

    public FriendRequestPayload() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public FriendRequestPayload(FriendRequestEntity entity) {
        this.id = entity.getId();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();
        this.approved = entity.getApproved();
        this.denied = entity.getDenied();
        this.expiredAt = entity.getExpiredAt();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public FriendRequestPayload(Long id, Long senderId, Long receiverId, Boolean approved,
                                Boolean denied, Date expiredAt) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
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

    @Override
    public FriendRequestEntity as() {
        return new FriendRequestEntity(id, senderId, receiverId, approved, denied, expiredAt, null);
    }
}
