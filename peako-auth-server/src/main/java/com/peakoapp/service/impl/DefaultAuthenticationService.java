package com.peakoapp.service.impl;

import com.peakoapp.core.exception.AccountExistedException;
import com.peakoapp.core.jwt.JwtTokenManager;
import com.peakoapp.core.jwt.TokenType;
import com.peakoapp.core.model.dto.AuthPayload;
import com.peakoapp.core.model.vo.LoginDetails;
import com.peakoapp.core.model.vo.SignupDetails;
import com.peakoapp.core.service.UserEntityService;
import com.peakoapp.service.AuthenticationService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationService.class);

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
        logger.debug("User::{}: Signing up with email and password", signupDetails.getEmail());
        signupDetails.setPassword(passwordEncoder.encode(signupDetails.getPassword()));
        return userEntityService
                .create(signupDetails.as())
                .map(payload -> createAccessToken(payload.getId()))
                .orElseThrow(() -> new AccountExistedException("The email address"
                        + signupDetails.getEmail() + " already existed."));
    }

    @Override
    public Map<String, String> signIn(LoginDetails loginDetails) {
        logger.debug("User::{}: Signing in with email and password", loginDetails.getEmail());
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(loginDetails.getEmail(),
                        loginDetails.getPassword());
        Authentication auth = authenticationProvider.authenticate(up);
        return createAccessToken(((AuthPayload) auth.getPrincipal()).getId());
    }

    private Map<String, String> createAccessToken(Long id) {
        HashMap<String, String> tokenMap = new HashMap<>();
        String token = JwtTokenManager.getProvider(TokenType.ACCESS).create(id);
        tokenMap.put("access_token", token);
        return tokenMap;
    }
}
