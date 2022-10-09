package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.model.dto.UserPayload;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The {@code ProfileDetails} class is a subset of {@link UserPayload} that represents a user's
 * profile data. It works with the controller layer to pass or receive the profile data.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileDetails implements Details<UserPayload>, Serializable {
    private static final long serialVersionUID = 2359580583895919279L;

    /**
     * The user id which identifies the owner of the profile details.
     * This field should only be used to search records in the database. It cannot be updated.
     */
    @NotNull
    private Long id;

    /**
     * The AWS S3 url of the profile picture that the user uploads.
     * This field should be set explicitly instead of relying on the user-passed JSON value through
     * the API calls.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profileUrl;

    /**
     * A short biography that the user writes.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @Size(max = 100)
    private String bio;

    /**
     * The first name of the user.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @NotBlank
    @Size(max = 40)
    private String firstName;

    /**
     * The last name of the user.
     * This field can be directly set by user through the passed JSON value, and it can be blank,
     * i.e., the user doesn't have to specify their last name.
     */
    @NotNull
    @Size(max = 40)
    private String lastName;

    /**
     * The current location of the user specified by themselves.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @NotBlank
    @Size(max = 40)
    private String location;

    public ProfileDetails() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public ProfileDetails(UserPayload payload) {
        this.id = payload.getId();
        this.profileUrl = payload.getProfileUrl();
        this.bio = payload.getBio();
        this.firstName = payload.getFirstName();
        this.lastName = payload.getLastName();
        this.location = payload.getLocation();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public ProfileDetails(Long id, String profileUrl, String bio, String firstName, String lastName,
                          String location) {
        this.id = id;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public UserPayload as() {
        return new UserPayload(id, null, null, null, profileUrl, bio, firstName, lastName, location,
                null, null, null, null);
    }
}
