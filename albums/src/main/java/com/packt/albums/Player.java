package com.packt.albums;

import java.time.LocalDate;

public record Player(String id, int jerseyNumber, String name, String position, LocalDate dateofBirth) {}
