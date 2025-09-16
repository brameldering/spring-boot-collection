package com.packt.albums;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("football")
public interface FootballClient {
  @RequestMapping(method = RequestMethod.GET, value = "/players")
  List<Player> getPlayers();

  @RequestMapping(method = RequestMethod.GET, value="/serviceinfo")
  String getServiceInfo();
}

