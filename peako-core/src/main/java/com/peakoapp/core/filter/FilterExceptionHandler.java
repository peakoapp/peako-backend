package com.peakoapp.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * The {@code FilterExceptionHandler} class handles all the possible exceptions thrown in the
 * {@link JwtTokenAuthenticationFilter}.
 *
 * @version 0.1.0
 */
@Component
public class FilterExceptionHandler extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FilterExceptionHandler.class);

    private final HandlerExceptionResolver handlerExceptionResolver;

    public FilterExceptionHandler(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.debug("FILTER::Exception::{}: {}", e.getClass(), e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
