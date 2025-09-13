package com.packt.footballobs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FootballConfiguration {

  @Bean
  public FileLoader fileLoader(FootballProperties footballProperties) {
    return new FileLoader(footballProperties);
  }

  @Bean
  public FootballCustomEndpoint footballCustomEndpoint(FileLoader fileLoader) {
    return new FootballCustomEndpoint(fileLoader);
  }

}
