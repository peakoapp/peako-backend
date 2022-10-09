package com.peakoapp.core.model.dto;

import com.peakoapp.core.model.entity.UserEntity;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 * The {@code AuthPayload} interfaces is a designed replacement for the Spring Security's
 * UserDetails and extends {@link Payload}.
 *
 * @version 0.1.0
 */
public interface AuthPayload extends Payload<UserEntity> {
    /**
     * Gets the unique identifier of the user.
     *
     * @return The user id.
     */
    Long getId();

    /**
     * Gets the unique login credential of the user.
     *
     * @return The email address of the user.
     */
    String getEmail();

    /**
     * Gets the password of the user.
     *
     * @return The raw password of the user.
     */
    String getPassword();

    /**
     * Indicates if the account has been marked deleted (logically deleted).
     *
     * @return False if the account has been deleted by the user; true, otherwise.
     */
    Boolean isNonDeleted();

    /**
     * Indicates if the account has been locked due to security reason(s).
     *
     * @return False if the account has been locked; true, otherwise.
     */
    Boolean isNonLocked();

    /**
     * Indicates if the account is active.
     *
     * @return True if the account is not locked nor deleted; False, otherwise.
     */
    Boolean isEnabled();

    /**
     * Gets the authorities that the account has.
     *
     * @return A set of authorities.
     */
    Collection<? extends GrantedAuthority> getAuthorities();
}
