package com.packt.footballobs.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DataService {
  private JdbcTemplate jdbcTemplate;

  public DataService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public String getPlayerStats(String player) {
    Random random = new Random();
    jdbcTemplate.execute("SELECT pg_sleep(" + random.nextDouble(1.0) + ")");
    return "some complex stats for player " + player;
  }
}
