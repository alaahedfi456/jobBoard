package com.awd.job.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/** Additional workshop check proving the Job service can execute a query against its database. */
@Component
public class JobDatabaseHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public JobDatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (Integer.valueOf(1).equals(result)) {
                return Health.up().withDetail("databaseQuery", "SELECT 1 succeeded").build();
            }
            return Health.down().withDetail("databaseQuery", "Unexpected query result").build();
        } catch (Exception exception) {
            return Health.down(exception).withDetail("databaseQuery", "SELECT 1 failed").build();
        }
    }
}
