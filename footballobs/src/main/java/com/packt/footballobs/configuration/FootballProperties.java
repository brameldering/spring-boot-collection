package com.packt.footballobs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "football")
public class FootballProperties {
  private String folder;

  public FootballProperties(String folder) {
    this.folder = folder;
  }

  public String getFolder() {
    return folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }
}