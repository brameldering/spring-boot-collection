package com.packt.football.services;

import com.packt.football.exceptions.AlreadyExistsException;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.model.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FootballService {

  private final Map<String, Player> players;

  public FootballService() {
    this.players = new HashMap<>();
    players.put("1884823", new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13)));
    players.put("325636", new Player("325636", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 02, 04)));
  }

  public List<Player> getPlayers() {
    return players.values().stream().collect(Collectors.toList());
  }

  public Player getPlayer(String id) {
    Player player = players.get(id);
    if (player == null) {
      throw new NotFoundException("Player with id " + id + " not found");
    }
    return player;
  }

  public Player addPlayer(Player player) {
    if (players.containsKey(player.id())) {
      throw new AlreadyExistsException("Player with id " + player.id() + " already exists");
    } else {
      players.put(player.id(), player);
      return player;
    }
  }

  public Player updatePlayer(Player player) {
    if (! players.containsKey(player.id())) {
      throw new NotFoundException("Player with id " + player.id() + " not found");
    } else {
      players.put(player.id(), player);
      return player;
    }
  }

  public void removePlayer(String id) {
    if (players.containsKey(id)) {
      players.remove(id);
    }
  }

}
