package com.packt.footballobs.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FootballHealthIndicator implements HealthIndicator {
  private JdbcTemplate jdbcTemplate;

  public FootballHealthIndicator(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Health health() {
    try {
      jdbcTemplate.execute("select 1");
      return Health.up().build();
    } catch (DataAccessException e) {
      return Health.down().withDetail("Cannot connect to DB", e).build();
    }
  }

}
