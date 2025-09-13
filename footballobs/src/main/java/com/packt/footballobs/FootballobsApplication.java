package com.packt.footballobs;

import com.packt.footballobs.configuration.FootballProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FootballProperties.class)
@SpringBootApplication
public class FootballobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballobsApplication.class, args);
	}

}
