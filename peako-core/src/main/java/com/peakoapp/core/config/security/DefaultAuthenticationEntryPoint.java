package com.peakoapp.core.config.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peakoapp.core.constant.enums.ReCode;
import com.peakoapp.core.utils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * The {@code DefaultAuthenticationEntryPoint} class sets the response status to 401.
 *
 * @version 0.1.0
 */
@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new R<>(ReCode.UNAUTHORIZED, null));
        response.getWriter().write(json);
    }
}
