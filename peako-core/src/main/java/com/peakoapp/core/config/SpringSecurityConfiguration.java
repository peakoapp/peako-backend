package com.peakoapp.core.config;

import com.peakoapp.core.config.security.DefaultAccessDeniedHandler;
import com.peakoapp.core.config.security.DefaultAuthenticationEntryPoint;
import com.peakoapp.core.config.security.LocalAuthenticationProvider;
import com.peakoapp.core.filter.FilterExceptionHandler;
import com.peakoapp.core.filter.JwtTokenAuthenticationFilter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * The SpringSecurityConfiguration manages all security related configurations except
 * {@link com.peakoapp.core.config.PasswordEncoderConfiguration PasswordEncoderConfiguration}.
 *
 * @version 0.1.0
 * @since 0.1.0
 */
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;
    private final LocalAuthenticationProvider localAuthenticationProvider;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final FilterExceptionHandler filterExceptionHandler;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public SpringSecurityConfiguration(DefaultAccessDeniedHandler defaultAccessDeniedHandler,
                                       DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint,
                                       LocalAuthenticationProvider localAuthenticationProvider,
                                       JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter,
                                       FilterExceptionHandler filterExceptionHandler) {
        this.defaultAccessDeniedHandler = defaultAccessDeniedHandler;
        this.defaultAuthenticationEntryPoint = defaultAuthenticationEntryPoint;
        this.localAuthenticationProvider = localAuthenticationProvider;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
        this.filterExceptionHandler = filterExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("*"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(false);  // avoid CSRF vulnerabilities

        http.cors().configurationSource(r -> cors)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST,
                        "/v1/auth/signup", "/v1/auth/signin", "/v1/auth/password"
                ).permitAll()
                .mvcMatchers(HttpMethod.PUT,
                        "/v1/auth/password"
                ).permitAll()
                .mvcMatchers(HttpMethod.GET,
                        "/v1/auth/email"
                ).permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(defaultAccessDeniedHandler)
                .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtTokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, JwtTokenAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(localAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
