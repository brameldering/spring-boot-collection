package com.packt.restclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"football.api.url=http://localhost:7979"})
public class FootballClientServiceTest {

  private static WireMockServer wireMockServer;

  @BeforeAll
  static void init() {
    wireMockServer = new WireMockServer(7979);
    wireMockServer.start();
    WireMock.configureFor(7979);
  }

  @Autowired
  private FootballClientService footballClientService;

  @Test
  public void getPlayerTest() {
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/players/123456"))
        .willReturn(WireMock.aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {
                "id": "123456",
                "jerseyNumber": 11,
                "name": "Alexia PUTELLAS",
                "position": "Midfielder",
                "dateOfBirth": "1994-02-04"
             }
                """))
        );
    Optional<Player> player = footballClientService.getPlayer("123456");

    Player expectedPlayer = new Player("123456", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 2, 4));
    assertEquals(expectedPlayer, player.get());
  }

  @Test
  public void getPlayersTest() {

    // ARRANGE
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/players"))
        .willReturn(WireMock.aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("""
                                [
                                    {
                                        "id": "325636",
                                        "jerseyNumber": 11,
                                        "name": "Alexia PUTELLAS",
                                        "position": "Midfielder",
                                        "dateOfBirth": "1994-02-04"
                                    },
                                    {
                                        "id": "396930",
                                        "jerseyNumber": 2,
                                        "name": "Ona BATLLE",
                                        "position": "Defender",
                                        "dateOfBirth": "1999-06-10"
                                    }
                                ]""")));

    // ACT
    List<Player> players = footballClientService.getPlayers();
    // ASSERT
    assertEquals(2, players.size());
    List<Player> expectedPlayers = List.of(
        new Player("325636", 11, "Alexia PUTELLAS", "Midfielder", LocalDate.of(1994, 2, 4)),
        new Player("396930", 2, "Ona BATLLE", "Defender", LocalDate.of(1999, 6, 10)));
    assertArrayEquals(expectedPlayers.toArray(), players.toArray());
  }

  @Test
  public void getPlayer_notFound() {

    // ARRANGE
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/players/8888"))
        .willReturn(WireMock.aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(404)));

    // ACT
    Optional<Player> player = footballClientService.getPlayer("8888");
    // ASSERT
    assertTrue(player.isEmpty());
  }

}
