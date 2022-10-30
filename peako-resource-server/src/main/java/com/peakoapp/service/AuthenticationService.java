package com.peakoapp.service;

import com.peakoapp.core.model.dto.AuthPayload;

/**
 * The {@code AuthenticationService} interface defines the method to obtain the authentication
 * principal from the security context in the resource server.
 *
 * @version 0.1.0
 */
public interface AuthenticationService {
    /**
     * Obtains the authentication principal from the security context.
     *
     * @return A custom principal object.
     */
    AuthPayload getPrincipal();
}
