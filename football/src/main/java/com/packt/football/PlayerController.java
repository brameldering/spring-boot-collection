package com.packt.football;

import com.packt.football.exceptions.AlreadyExistsException;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.model.Player;
import com.packt.football.services.FootballService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/players")
@RestController
public class PlayerController {

  private FootballService footballService;

  public PlayerController(FootballService footballService) {
    this.footballService = footballService;
  }

  @GetMapping
  public List<Player> getPlayers() {
    return footballService.getPlayers();
  }

  @GetMapping("/{id}")
  public Player getPlayerById(@PathVariable String id) {
    return footballService.getPlayer(id);
  }

  @PostMapping
  public void createPlayer(@RequestBody Player player) {
    footballService.addPlayer(player);
  }

  @PutMapping("/{id}")
  public void updatePlayer(@PathVariable String id, @RequestBody Player player) {
    footballService.updatePlayer(player);
  }

  @DeleteMapping("/{id}")
  public void deletePlayer(@PathVariable String id) {
    footballService.removePlayer(id);
  }

}
