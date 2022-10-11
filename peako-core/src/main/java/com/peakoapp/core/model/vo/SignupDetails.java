package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.constant.constnt.Regex;
import com.peakoapp.core.constant.enums.IdentityProvider;
import com.peakoapp.core.model.dto.UserPayload;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The {@code SignupDetails} class is a subset of {@link UserPayload} that represents the fields
 * that a user will input in the signup process. Because this will not be used anywhere else, all
 * the properties of this class will never be serialized.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignupDetails implements Details<UserPayload>, Serializable {
    private static final long serialVersionUID = -8420271732931107533L;

    /**
     * The email which the user uses to sign in their account.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @Size(max = 40)
    @Email(regexp = Regex.EMAIL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    /**
     * The unencrypted password which the user uses to sign in their account.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @NotBlank
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * The first name of the user.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @NotBlank
    @Size(max = 40)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String firstName;

    /**
     * The last name of the user.
     * This field can be directly set by user through the passed JSON value, and it can be blank,
     * i.e., the user doesn't have to specify their last name.
     */
    @Size(max = 40)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String lastName;

    /**
     * The identity provider which the user uses to sign in their account.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private IdentityProvider provider;

    public SignupDetails() {
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public SignupDetails(String email, String password, String firstName, String lastName,
                         IdentityProvider provider) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public IdentityProvider getProvider() {
        return provider;
    }

    public void setProvider(IdentityProvider provider) {
        this.provider = provider;
    }

    @Override
    public UserPayload as() {
        UserPayload payload = new UserPayload();
        payload.setEmail(email);
        payload.setPassword(password);
        payload.setFirstName(firstName);
        payload.setProvider(provider);
        if (lastName != null) {
            payload.setLastName(lastName);
        }
        return payload;
    }
}
