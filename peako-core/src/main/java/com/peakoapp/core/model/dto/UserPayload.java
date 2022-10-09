package com.peakoapp.core.model.dto;

import com.peakoapp.core.constant.enums.IdentityProvider;
import com.peakoapp.core.model.entity.UserEntity;
import java.io.Serializable;

/**
 * The {@code UserPayload} class is a subset of {@link UserEntity}, which works with services
 * implemented in other layers.
 *
 * @version 0.1.0
 */
public class UserPayload implements Serializable, Payload<UserEntity> {
    private Long id;
    private String email;
    private Boolean emailVerified;
    private String password;
    private String profileUrl;
    private String bio;
    private String firstName;
    private String lastName;
    private String location;
    private IdentityProvider provider;
    private Boolean nonDeleted;
    private Boolean nonLocked;
    private Boolean enabled;

    /**
     * Initializes fields with default values specified in the database.
     */
    public UserPayload() {
        this.emailVerified = false;
        this.profileUrl = "www.defaulturl.com";  // TODO: replace with real default profile url
        this.bio = "";
        this.lastName = "";
        this.location = "";
        this.provider = IdentityProvider.LOCAL;
        this.nonDeleted = true;
        this.nonLocked = true;
        this.enabled = true;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public UserPayload(UserEntity entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.emailVerified = entity.getEmailVerified();
        this.password = entity.getPassword();
        this.profileUrl = entity.getProfileUrl();
        this.bio = entity.getBio();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.location = entity.getLocation();
        this.provider = entity.getProvider();
        this.nonDeleted = entity.getNonDeleted();
        this.nonLocked = entity.getNonLocked();
        this.enabled = entity.getEnabled();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public UserPayload(Long id, String email, Boolean emailVerified, String password,
                       String profileUrl, String bio, String firstName, String lastName,
                       String location, IdentityProvider provider, Boolean nonDeleted,
                       Boolean nonLocked, Boolean enabled) {
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

    @Override
    public UserEntity as() {
        return new UserEntity(id, email, emailVerified, password, profileUrl, bio, firstName,
                lastName, location, provider, nonDeleted, nonLocked, enabled, null, null, null);
    }
}
