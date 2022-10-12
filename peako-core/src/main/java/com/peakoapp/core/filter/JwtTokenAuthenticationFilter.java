package com.peakoapp.core.filter;

import com.peakoapp.core.exception.AccountDeletedException;
import com.peakoapp.core.exception.AccountDisabledException;
import com.peakoapp.core.exception.AccountLockedException;
import com.peakoapp.core.exception.AccountNotExistException;
import com.peakoapp.core.jwt.JwtToken;
import com.peakoapp.core.jwt.JwtTokenManager;
import com.peakoapp.core.jwt.TokenType;
import com.peakoapp.core.service.UserEntityService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The {@code JwtTokenAuthenticationFilter} class filters requests with unexpired and recognized jwt
 * tokens and sets the Authentication Principal for them.
 *
 * @version 0.1.0
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    private static final String SCHEME = "Bearer";
    private static final String HEADER_NAME = "Authorization";

    private final UserEntityService userEntityService;

    public JwtTokenAuthenticationFilter(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HEADER_NAME);
        if (checkHeader(header) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String authToken = header.substring(SCHEME.length()).trim();
            JwtToken jwtToken = JwtTokenManager.getProvider(TokenType.ACCESS).parse(authToken);
            logger.debug("FILTER::JwtTokenProvider: Valid access token");
            userEntityService.getById(jwtToken.getSubject()).map(payload -> {
                logger.debug("FILTER::User::{}: Found", payload.getId());
                if (!payload.isNonDeleted()) {
                    logger.debug("FILTER::User::{}: Deleted account access", payload.getId());
                    throw new AccountDeletedException("The account has been deleted.");
                } else if (!payload.isNonLocked()) {
                    logger.debug("FILTER::User::{}: Locked account access", payload.getId());
                    throw new AccountLockedException("The account has been locked.");
                } else if (!payload.isEnabled()) {
                    logger.debug("FILTER::User::{}: Disabled account access", payload.getId());
                    throw new AccountDisabledException("The account has been disabled.");
                } else {
                    logger.debug("FILTER::User::{}: Deleted account access", payload.getId());
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(payload, null,
                                    payload.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    return payload;  // return value has no effect
                }
            }).orElseThrow(() -> {
                logger.debug("FILTER::User::{}: Couldn't find", jwtToken.getSubject());
                throw new AccountNotExistException("The account does not exist.");
            });
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkHeader(String header) {
        return header != null && header.startsWith(SCHEME);
    }
}
