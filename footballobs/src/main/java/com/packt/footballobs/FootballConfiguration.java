package com.packt.footballobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FootballProperties.class)
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
