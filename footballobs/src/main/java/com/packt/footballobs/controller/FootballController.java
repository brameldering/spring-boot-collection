package com.packt.footballobs.controller;

import com.packt.footballobs.service.FileLoader;
import com.packt.footballobs.service.TradingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/football")
public class FootballController {
  private final TradingService tradingService;
  private FileLoader fileLoader;

  public FootballController(FileLoader fileLoader, TradingService tradingService) {
    this.fileLoader = fileLoader;
    this.tradingService = tradingService;
  }

  @GetMapping
  public List<String> getTeams() {
    return fileLoader.getTeams();
  }

  @PostMapping
  public int tradeCards(@RequestBody int orders) {
    return tradingService.tradeCards(orders);
  }
}
