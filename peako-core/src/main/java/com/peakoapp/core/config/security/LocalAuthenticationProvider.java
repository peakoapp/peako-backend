package com.peakoapp.core.config.security;

import com.peakoapp.core.exception.AccountDeletedException;
import com.peakoapp.core.exception.AccountDisabledException;
import com.peakoapp.core.exception.AccountLockedException;
import com.peakoapp.core.exception.AccountNotExistException;
import com.peakoapp.core.exception.CoreException;
import com.peakoapp.core.service.UserEntityService;
import java.lang.reflect.Constructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The {@code LocalAuthenticationProvider} provides a way to authenticate users with user-given
 * email and password pair.
 *
 * @version 0.1.0
 */
@Component
public class LocalAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserEntityService userEntityService;

    public LocalAuthenticationProvider(PasswordEncoder passwordEncoder,
                                       UserEntityService userEntityService) {
        this.passwordEncoder = passwordEncoder;
        this.userEntityService = userEntityService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String givenEmail = auth.getPrincipal().toString();
        String rawPassword = auth.getCredentials().toString();

        return userEntityService.getByEmail(givenEmail).map(payload -> {
            if (!payload.isNonDeleted()) {
                throw exception(AccountDeletedException.class, givenEmail);
            } else if (!payload.isNonLocked()) {
                throw exception(AccountLockedException.class, givenEmail);
            } else if (!payload.isEnabled()) {
                throw exception(AccountDisabledException.class, givenEmail);
            } else if (!passwordEncoder.matches(rawPassword, payload.getPassword())) {
                throw exception(BadCredentialsException.class, givenEmail);
            } else {
                return new UsernamePasswordAuthenticationToken(payload, null,
                        payload.getAuthorities());
            }
        }).orElseThrow(() -> {
            throw exception(AccountNotExistException.class, givenEmail);
        });
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private <T extends AuthenticationException> RuntimeException exception(Class<T> clazz, String email) {
        String msg = "The account associated with the email address ({}) ";
        Constructor<T> constructor;

        try {
            constructor = clazz.getConstructor(String.class);
            if (clazz.isAssignableFrom(AccountDeletedException.class)) {
                msg += "has been logically deleted.";
            } else if (clazz.isAssignableFrom(AccountLockedException.class)) {
                msg += "has been locked due to security reason(s).";
            } else if (clazz.isAssignableFrom(AccountDisabledException.class)) {
                msg += "has been disabled.";
            } else if (clazz.isAssignableFrom(AccountNotExistException.class)) {
                msg += "does not exist";
            } else if (clazz.isAssignableFrom(BadCredentialsException.class)) {
                msg += "has a different password than the one provided by the user.";
            } else {
                return new CoreException("The exception (" + clazz + ") is not found.");
            }
            return constructor.newInstance(String.format(msg, email));
        } catch (Exception ex) {
            return new CoreException("An unknown exception was thrown during authentication.");
        }
    }
}
