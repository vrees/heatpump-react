package de.vrees.heatpump.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("de.vrees.heatpump.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfigurationMariaDB {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfigurationMariaDB.class);
}
