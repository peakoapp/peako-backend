package com.peakoapp.core.model.entity;

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
 * The {@code FriendshipRelation} is a relation between {@link UserEntity}s that represents
 * friendships.
 *
 * @version 0.1.0
 */
@Entity
@Table(name = "peako_friendship")
@EntityListeners(AuditingEntityListener.class)
public class FriendshipRelation {
    @Id
    @GeneratedValue(generator = "SnowFlakeGenerator")
    @GenericGenerator(
            name = "SnowFlakeGenerator",
            strategy = "com.peakoapp.core.utils.SnowFlakeGenerator"
    )
    @Column(name = "friendship_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "friend_id", nullable = false)
    private Long friendId;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    public FriendshipRelation() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public FriendshipRelation(Long id, Long userId, Long friendId, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
