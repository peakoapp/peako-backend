package com.peakoapp.core.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The {@code FriendRequestEntity} class represents a typical friend request.
 *
 * @version 0.1.0
 */
@Entity
@Table(name = "peako_friend_request")
@EntityListeners(AuditingEntityListener.class)
public class FriendRequestEntity implements Serializable, Entirety {
    private static final long serialVersionUID = 4653822830413140040L;

    @Id
    @GeneratedValue(generator = "SnowFlakeGenerator")
    @GenericGenerator(
            name = "SnowFlakeGenerator",
            strategy = "com.peakoapp.core.utils.SnowFlakeGenerator"
    )
    @Column(name = "request_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "sender_id", nullable = false, updatable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false, updatable = false)
    private Long receiverId;

    @Column(name = "approved", nullable = false)
    private Boolean approved;

    @Column(name = "denied", nullable = false)
    private Boolean denied;

    @Column(name = "expired_at", nullable = false, updatable = false)
    private Date expiredAt;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    public FriendRequestEntity() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public FriendRequestEntity(Long id, Long senderId, Long receiverId, Boolean approved,
                               Boolean denied, Date expiredAt, Date createTime) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.approved = approved;
        this.denied = denied;
        this.expiredAt = expiredAt;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
