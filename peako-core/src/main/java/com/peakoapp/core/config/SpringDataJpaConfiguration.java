package com.peakoapp.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The {@code SpringDataJpaConfiguration} class contains all the necessary configurations for the
 * Spring Data Jpa framework to work properly.
 *
 * @version 0.1.0
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class SpringDataJpaConfiguration {
}
