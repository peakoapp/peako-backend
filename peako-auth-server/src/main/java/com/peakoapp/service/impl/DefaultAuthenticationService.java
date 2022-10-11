package com.peakoapp.service.impl;

import com.peakoapp.core.exception.AccountExistedException;
import com.peakoapp.core.exception.CoreException;
import com.peakoapp.core.jwt.JwtTokenManager;
import com.peakoapp.core.jwt.TokenType;
import com.peakoapp.core.model.dto.AuthPayload;
import com.peakoapp.core.model.vo.LoginDetails;
import com.peakoapp.core.model.vo.SignupDetails;
import com.peakoapp.core.service.UserEntityService;
import com.peakoapp.service.AuthenticationService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code DefaultAuthenticationService} class is the default implementation of
 * {@link AuthenticationService} and provides basic functionalities for login, signup, etc.
 *
 * @version 0.1.0
 */
@Transactional(rollbackFor = Exception.class)
@Primary
@Service(value = "defaultAuthenticationService")
public class DefaultAuthenticationService implements AuthenticationService {
    private final UserEntityService userEntityService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public DefaultAuthenticationService(UserEntityService userEntityService,
                                        PasswordEncoder passwordEncoder,
                                        AuthenticationProvider authenticationProvider) {
        this.userEntityService = userEntityService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Map<String, String> signUp(SignupDetails signupDetails) {
        userEntityService.getByEmail(signupDetails.getEmail()).ifPresent(payload -> {
            throw new AccountExistedException("The account associated with " + payload.getEmail()
                    + " has already existed.");
        });
        signupDetails.setPassword(passwordEncoder.encode(signupDetails.getPassword()));
        return userEntityService.create(signupDetails.as()).map(payload -> {
            HashMap<String, String> accessKeyMap = new HashMap<>();
            String accessKey = JwtTokenManager.getProvider(TokenType.ACCESS).create(payload.getId());
            accessKeyMap.put("access_key", accessKey);
            return accessKeyMap;
        }).orElseThrow(() -> {
            throw new CoreException("Failed to create a new account.");
        });
    }

    @Override
    public Map<String, String> signIn(LoginDetails loginDetails) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(loginDetails.getEmail(),
                        loginDetails.getPassword());
        Authentication auth = authenticationProvider.authenticate(up);
        AuthPayload payload = (AuthPayload) auth.getPrincipal();
        HashMap<String, String> accessKeyMap = new HashMap<>();
        String accessKey = JwtTokenManager.getProvider(TokenType.ACCESS).create(payload.getId());
        accessKeyMap.put("access_key", accessKey);
        return accessKeyMap;
    }
}
