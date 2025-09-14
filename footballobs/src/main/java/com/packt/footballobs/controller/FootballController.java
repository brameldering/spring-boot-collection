package com.packt.footballobs.controller;

import com.packt.footballobs.service.AuctionService;
import com.packt.footballobs.service.DataService;
import com.packt.footballobs.service.FileLoader;
import com.packt.footballobs.service.TradingService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/football")
public class FootballController {
  private final TradingService tradingService;
  private FileLoader fileLoader;
  private final ObservationRegistry observationRegistry;
  private DataService dataService;
  private AuctionService auctionService;

  private static final Logger logger = LoggerFactory.getLogger(FootballController.class);
  private static Random random = new Random();

  public FootballController(FileLoader fileLoader, TradingService tradingService, ObservationRegistry observationRegistry, DataService dataService, AuctionService auctionService) {
    this.fileLoader = fileLoader;
    this.tradingService = tradingService;
    this.observationRegistry = observationRegistry;
    this.dataService = dataService;
    this.auctionService = auctionService;
  }

  @GetMapping
  public List<String> getTeams() {
    return fileLoader.getTeams();
  }

  @PostMapping
  public int tradeCards(@RequestBody int orders) {
    return tradingService.tradeCards(orders);
  }

  @GetMapping("ranking/{player}")
  public int getRanking(@PathVariable("player") String player) {
    logger.info("Preparing ranking for player {}", player);

//    if (random.nextInt(100) > 94) {
//      throw new RuntimeException("It's not possible to get the ranking for player " + player
//          + " at this moment. Please try again later.");
//    }

    Observation collectObservation = Observation.createNotStarted("collect", observationRegistry);
    collectObservation.lowCardinalityKeyValue("player", player);
    collectObservation.observe(() -> {
      try {
        logger.info("Simulate a data collection for player {}", player);
        Thread.sleep(random.nextInt(100));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    Observation processObservation = Observation.createNotStarted("process", observationRegistry);
    processObservation.lowCardinalityKeyValue("player", player);
    processObservation.observe(() -> {
      try {
        logger.info("Simulate a data processing for player {}", player);
        Thread.sleep(random.nextInt(100));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    return random.nextInt(1000);
  }

  @GetMapping("/stats/{player}")
  public String getStats(@PathVariable("player") String player) {
    return dataService.getPlayerStats(player);
  }

  @PostMapping("/bid/{player}")
    public void addBid(@PathVariable("player") String player, @RequestBody String bid) {
//      auctionService.addBid(player, bid);
        auctionService.addBidAOP(player, bid);
  }

}
