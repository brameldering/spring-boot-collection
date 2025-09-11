package com.packt.football;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.packt.football.exceptions.AlreadyExistsException;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.model.Player;
import com.packt.football.services.FootballService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PlayerController.class)
public class PlayerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private FootballService footballService;

  private List<Player> players;

  @BeforeEach
  public void setup() {
    Player player1 = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
    Player player2 = new Player("325636", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 02, 04));
    players = List.of(player1, player2);
  }

  @Test
  public void testListPlayers() throws Exception {
    given(footballService.getPlayers()).willReturn(players);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/players").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))).andReturn();

    String json = result.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    List<Player> returnedPlayers = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Player.class));
    assertArrayEquals(players.toArray(), returnedPlayers.toArray());
  }

  @Test
  public void testReadPlayer_exist() throws Exception {
    // ARRANGE
    Player player = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
    given(footballService.getPlayer("1884823"))
        .willReturn(player);
    String id = "1884823";
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    // ACT & ASSERT
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/players/" + id).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Ivana ANDRES"))
        .andReturn();

    Player returnedPlayer = mapper.readValue(result.getResponse().getContentAsString(), Player.class);
    assertEquals(player, returnedPlayer);
  }

  @Test
  public void testReadPlayer_doesnt_exist() throws Exception {
    String id = "1884823";
    given(footballService.getPlayer(id)).willThrow(new NotFoundException("Player not found"));

    mockMvc.perform(MockMvcRequestBuilders.get("/players/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  @Test
  public void testUpdatePlayer_exists() throws Exception {
    // ARRANGE
    Player player = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
    given(footballService.updatePlayer(player)).willReturn(player);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    // ACT & ASSERT
    mockMvc.perform(
            MockMvcRequestBuilders.put("/players/" + player.id())
                .content(mapper.writeValueAsString(player))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdatePlayer_doesnt_exists() throws Exception {
    // ARRANGE
    Player player = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
    given(footballService.updatePlayer(player)).willThrow(new NotFoundException("Player not found"));
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    // ACT & ASSERT
    mockMvc.perform(
            MockMvcRequestBuilders.put("/players/" + player.id())
                .content(mapper.writeValueAsString(player))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testCreatePlayer() throws Exception {
    // ARRANGE
    Player player = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
    given(footballService.addPlayer(player)).willReturn(player);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    // ACT & ASSERT
    mockMvc.perform(
            MockMvcRequestBuilders.post("/players")
                .content(mapper.writeValueAsString(player))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testCreatePlayer_already_exists() throws Exception {
    // ARRANGE
    Player player = new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 07, 13));
    given(footballService.addPlayer(player)).willThrow(new AlreadyExistsException("The player already exists"));
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    // ACT & ASSERT
    mockMvc.perform(
            MockMvcRequestBuilders.post("/players")
                .content(mapper.writeValueAsString(player))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
