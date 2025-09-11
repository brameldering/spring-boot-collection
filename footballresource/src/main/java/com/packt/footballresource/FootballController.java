package com.packt.footballresource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/football")
@RestController
public class FootballController {

  @GetMapping("/teams")
  public List<String> getTeams() {
    return List.of("Argentina", "Australia", "Brazil", "Canada", "China PR", "Colombia", "Costa Rica", "Denmark", "England", "France", "Germany", "Italy", "Jamaica", "Japan", "Korea Republic", "Morocco", "Netherlands", "New Zealand", "Nigeria", "Norway", "Panama", "Philippines", "Portugal", "Republic of Ireland", "South Africa", "Spain", "Sweden", "Switzerland", "USA", "Vietnam", "Zambia");
  }
}
