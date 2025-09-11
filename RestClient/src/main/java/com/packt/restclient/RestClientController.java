package com.packt.restclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/albums")
@RestController
public class RestClientController {

  private FootballClientService footballClientService;

  public RestClientController(FootballClientService footballClientService) {
    this.footballClientService = footballClientService;
  }

  @GetMapping("/players")
  public List<Player> getPlayers() {
    return footballClientService.getPlayers();
  }

  @GetMapping("/players/{id}")
  public Optional<Player> getPlayer(@PathVariable String id) {
    return footballClientService.getPlayer(id);
  }
}
