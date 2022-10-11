package com.peakoapp.service;

import com.peakoapp.core.model.vo.LoginDetails;
import com.peakoapp.core.model.vo.SignupDetails;
import java.util.Map;

/**
 * The {@code AuthenticationService} interface defines the methods that should be supported in the
 * authorization server.
 *
 * @version 0.1.0
 */
public interface AuthenticationService {
    /**
     * Creates a new account with the given details and returns a generated access token.
     *
     * @param signupDetails The basic account information provided by the user.
     * @return A map that contains the generated access token.
     */
    Map<String, String> signUp(SignupDetails signupDetails);

    /**
     * Authenticates the given email address with the given password and returns a generated access
     * token.
     *
     * @param loginDetails The basic account information provided by the user.
     * @return A map that contains the generated access token.
     */
    Map<String, String> signIn(LoginDetails loginDetails);
}
