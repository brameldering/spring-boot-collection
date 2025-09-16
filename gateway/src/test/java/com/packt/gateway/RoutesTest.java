package com.packt.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "PLAYERS_URI=http://localhost:${wiremock.server.port}",
        "ALBUMS_URI=http://localhost:${wiremock.server.port}"
})
public class RoutesTest {
  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void playersRouteTest() throws Exception {
    final String testBody = """
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
     ]
     """;

    // Arrange
    stubFor(
      get(urlEqualTo("/players"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(testBody)));

    // Act & Assert
    webTestClient.get().uri("/api/players").exchange()
        .expectStatus().isOk()
        .expectBody().json(testBody)
        .jsonPath("$[0].name").isEqualTo("Alexia PUTELLAS")
        .jsonPath("$[1].name").isEqualTo("Ona BATLLE");;
  }

  public void albimsRouteTest() throws Exception {
    final String testBody = """
        [
            "Album1",
            "Album2",
        ]""";

    // Arrange
    stubFor(get(urlEqualTo("/albums"))
    .willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withBody(testBody)));

    // Act & Assert
    webTestClient.get().uri("/api/albums").exchange()
        .expectStatus().isOk()
        .expectBody().json(testBody)
        .jsonPath("$[0]").isEqualTo("Album1")
        .jsonPath("$[1]").isEqualTo("Album2");
  }

  @Test
  public void invalidRouteTest() throws Exception {
    // Act & Assert
    webTestClient.get().uri("/api/invalid").exchange()
        .expectStatus().isNotFound();
  }
}
