package com.packt.footballobs.configuration;

import com.packt.footballobs.actuator.FootballCustomEndpoint;
import com.packt.footballobs.service.FileLoader;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
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

  @Bean
  public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }

}
