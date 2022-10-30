package com.peakoapp.service.impl;

import com.peakoapp.core.model.dto.AuthPayload;
import com.peakoapp.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The {@code DefaultAuthenticationService} class is the default implementation of
 * {@link AuthenticationService} and provides a uniform way to obtain the authentication principal.
 *
 * @version 0.1.0
 */
@Primary
@Service(value = "defaultAuthenticationService")
public class DefaultAuthenticationService implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationService.class);

    @Override
    public AuthPayload getPrincipal() {
        logger.debug("Authentication: Retrieving authentication principal from the context");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            logger.debug("Authentication: Null");
            return null;
        } else {
            logger.debug("Authentication: Found");
            return (AuthPayload) authentication.getPrincipal();
        }
    }
}
