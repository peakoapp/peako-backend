package com.peakoapp.core.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The {@code PasswordEncoderConfiguration} is extracted from SpringSecurityConfiguration to avoid
 * circular dependency problems.
 *
 * @version 0.1.0
 */
@Configuration
public class PasswordEncoderConfiguration {
    /**
     * Delegate available password encoders. By default, the currently in-use encoder is bcrypt.
     *
     * @return DelegatingPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
