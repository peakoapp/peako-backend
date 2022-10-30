package com.peakoapp.service.impl;

import com.peakoapp.core.model.dto.UserPayload;
import com.peakoapp.core.model.vo.AccountDetails;
import com.peakoapp.core.model.vo.ProfileDetails;
import com.peakoapp.core.service.UserEntityService;
import com.peakoapp.service.AuthenticationService;
import com.peakoapp.service.UserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code DefaultUserService} class is the default implementation of {@link UserService} and
 * provides the basic CRUD functionalities.
 *
 * @version 0.1.0
 */
@Transactional(rollbackFor = Exception.class)
@Primary
@Service(value = "defaultUserService")
public class DefaultUserService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserEntityService userEntityService;
    private final AuthenticationService authenticationService;

    public DefaultUserService(UserEntityService userEntityService,
                              AuthenticationService authenticationService) {
        this.userEntityService = userEntityService;
        this.authenticationService = authenticationService;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AccountDetails> getAccount() {
        UserPayload authPayload = (UserPayload) authenticationService.getPrincipal();
        if (authPayload != null) {
            return Optional.of(new AccountDetails(authPayload));
        } else {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProfileDetails> getProfileById(Long userId) {
        logger.debug("User::{}: Retrieving the profile details", userId);
        return userEntityService.getById(userId).map(ProfileDetails::new);
    }
}
