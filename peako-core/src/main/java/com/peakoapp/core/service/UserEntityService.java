package com.peakoapp.core.service;

import com.peakoapp.core.model.dto.UserPayload;

/**
 * The {@code UserEntityService} interface provides basic methods for manipulating
 * {@link com.peakoapp.core.model.entity.UserEntity} in the database through {@link UserPayload}.
 *
 * @version 0.1.0
 */
public interface UserEntityService extends EntityService<UserPayload> {
    /**
     * Unlocks a locked account.
     *
     * @param id The user id.
     */
    void unlock(Long id);

    /**
     * Locks an account due to some security reason(s).
     *
     * @param id The user id.
     */
    void lock(Long id);

    /**
     * Recovers a logically deleted account.
     *
     * @param id The user id.
     */
    void recover(Long id);

    /**
     * Logically deletes an account.
     *
     * @param id The user id.
     */
    void markDelete(Long id);
}
