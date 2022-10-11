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
            userEntityService.getById(jwtToken.getSubject()).map(payload -> {
                if (!payload.isNonDeleted()) {
                    throw new AccountDeletedException("The account has been deleted.");
                } else if (payload.isNonLocked()) {
                    throw new AccountLockedException("The account has been locked.");
                } else if (!payload.isEnabled()) {
                    throw new AccountDisabledException("The account has been disabled.");
                } else {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(payload, null,
                                    payload.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    return payload;  // return value has no effect
                }
            }).orElseThrow(() -> {
                throw new AccountNotExistException("The account does not exist.");
            });
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkHeader(String header) {
        return header != null && header.startsWith(SCHEME);
    }
}
