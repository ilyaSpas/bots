package org.example.demotgbot.model;

import lombok.Data;

@Data
public class Weather {
    private Location location;
    private Current current;
}
