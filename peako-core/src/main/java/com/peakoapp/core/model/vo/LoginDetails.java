package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.constant.constnt.Regex;
import com.peakoapp.core.model.dto.UserPayload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The {@code LoginDetails} class is a subset of {@link UserPayload} that represents the fields
 * that a user will input in the login process. Because this will not be used anywhere else, all
 * the properties of this class will never be serialized.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginDetails implements Details<UserPayload> {
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

    public LoginDetails() {
    }

    public LoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public UserPayload as() {
        return new UserPayload(null, email, null, password, null, null, null, null, null, null,
                null, null, null);
    }
}
