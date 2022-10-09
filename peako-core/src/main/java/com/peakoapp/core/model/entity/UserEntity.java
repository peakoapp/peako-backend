package com.peakoapp.core.model.entity;

import com.peakoapp.core.constant.enums.IdentityProvider;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The {@code UserEntity} is a union of user profile data and account data.
 *
 * @version 0.1.0
 */
@Entity
@Table(name = "peako_user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Serializable, Entirety {
    @Id
    @GeneratedValue(generator = "SnowFlakeGenerator")
    @GenericGenerator(
            name = "SnowFlakeGenerator",
            strategy = "com.peakoapp.core.utils.SnowFlakeGenerator"
    )
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 40)
    private String email;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "profile_url", nullable = false, length = 1024)
    private String profileUrl;

    @Column(name = "bio", nullable = false, length = 100)
    private String bio;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "location", nullable = false, length = 40)
    private String location;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 15)
    private IdentityProvider provider;

    @Column(name = "non_deleted", nullable = false)
    private Boolean nonDeleted;

    @Column(name = "non_locked", nullable = false)
    private Boolean nonLocked;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public  UserEntity() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public UserEntity(Long id, String email, Boolean emailVerified, String password,
                      String profileUrl, String bio, String firstName, String lastName,
                      String location, IdentityProvider provider, Boolean nonDeleted,
                      Boolean nonLocked, Boolean enabled, Integer version, Date createTime, Date updateTime) {
        this.id = id;
        this.email = email;
        this.emailVerified = emailVerified;
        this.password = password;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.provider = provider;
        this.nonDeleted = nonDeleted;
        this.nonLocked = nonLocked;
        this.enabled = enabled;
        this.version = version;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public IdentityProvider getProvider() {
        return provider;
    }

    public void setProvider(IdentityProvider provider) {
        this.provider = provider;
    }

    public Boolean getNonDeleted() {
        return nonDeleted;
    }

    public void setNonDeleted(Boolean nonDeleted) {
        this.nonDeleted = nonDeleted;
    }

    public Boolean getNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(Boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
