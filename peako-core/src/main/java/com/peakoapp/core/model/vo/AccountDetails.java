package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.constant.constnt.Regex;
import com.peakoapp.core.constant.enums.IdentityProvider;
import com.peakoapp.core.model.dto.UserPayload;
import com.peakoapp.core.validation.group.Credentials;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * The {@code AccountDetails} class is a subset of {@link UserPayload} that represents a user's
 * account data. It works with the controller layer to pass or receive the account data.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountDetails implements Details<UserPayload>, Serializable {
    private static final long serialVersionUID = -539283611202698481L;

    /**
     * The user id which identifies the owner of the account.
     * This field should only be used to search records in the database. It cannot be updated.
     */
    @NotNull
    private Long id;

    /**
     * The email which the user uses to sign in their account.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull(groups = {Default.class, Credentials.class})
    @Email(regexp = Regex.EMAIL, groups = {Default.class, Credentials.class})
    private String email;

    /**
     * The verification state of the email address of the user.
     * This field should be set explicitly instead of relying on the user-passed JSON value through
     * the API calls.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean emailVerified;

    /**
     * The unencrypted password which the user uses to sign in their account.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull(groups = {Default.class, Credentials.class})
    @NotBlank(groups = {Default.class, Credentials.class})
    @Size(min = 8, groups = {Default.class, Credentials.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * The identity provider which the user uses to sign in their account.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    private IdentityProvider provider;

    /**
     * The deletion state of the account.
     * This field should be set explicitly instead of relying on the user-passed JSON value through
     * the API calls.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean nonDeleted;

    /**
     * The locking state of the account.
     * This field should be set explicitly instead of relying on the user-passed JSON value through
     * the API calls.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean nonLocked;

    /**
     * The general state of the account.
     * This field should be set explicitly instead of relying on the user-passed JSON value through
     * the API calls.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled;

    public AccountDetails() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public AccountDetails(UserPayload payload) {
        this.id = payload.getId();
        this.email = payload.getPassword();
        this.emailVerified = payload.getEmailVerified();
        this.password = payload.getPassword();
        this.provider = payload.getProvider();
        this.nonDeleted = payload.getNonDeleted();
        this.nonLocked = payload.getNonLocked();
        this.enabled = payload.getEnabled();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public AccountDetails(Long id, String email, Boolean emailVerified, String password,
                          IdentityProvider provider, Boolean nonDeleted, Boolean nonLocked,
                          Boolean enabled) {
        this.id = id;
        this.email = email;
        this.emailVerified = emailVerified;
        this.password = password;
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
    public UserPayload as() {
        return new UserPayload(id, email, emailVerified, password, null, null, null, null, null,
                provider, nonDeleted, nonLocked, enabled);
    }
}
