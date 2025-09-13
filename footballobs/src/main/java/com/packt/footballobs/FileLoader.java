package com.packt.footballobs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileLoader {
  private String fileName;
  private List<String> teams;
  private final FootballProperties footballProperties;

  // Inject the FootballProperties bean via the constructor
  public FileLoader(FootballProperties footballProperties) {
    this.footballProperties = footballProperties;
  }

  public String getFileName() {
    return fileName;
  }

  public List<String> getTeams() {
    return teams;
  }

  private void loadFile(String filename) throws Exception {
      this.fileName = filename;
      ObjectMapper objectMapper = new ObjectMapper();
      File file = new File(fileName);
      teams = objectMapper.readValue(file, new TypeReference<List<String>>() {});
  }

  public void loadFile() throws IOException {
    Files.list(Paths.get(footballProperties.getFolder()))
        .filter(Files::isRegularFile)
        .findFirst()
        .ifPresent(file -> {
          try {
            loadFile(file.toString());
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

}
