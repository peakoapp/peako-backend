package com.peakoapp.service;

import com.peakoapp.core.model.vo.AccountDetails;
import com.peakoapp.core.model.vo.ProfileDetails;
import java.util.Optional;

/**
 * The {@code UserService} interface defines the methods that provide the basic CRUD functionalities
 * on the user information.
 *
 * @version 0.1.0
 */
public interface UserService {
    /**
     * Gets the account information of the authenticated user.
     *
     * @return An instance of {@link AccountDetails}.
     */
    Optional<AccountDetails> getAccount();

    /**
     * Gets the profile details of the given user.
     *
     * @param userId The id of the user to be searched for.
     * @return An instance of {@link ProfileDetails}.
     */
    Optional<ProfileDetails> getProfileById(Long userId);
}
