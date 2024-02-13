package org.example.demotgbot.model;

import lombok.Data;

@Data
public class Location {
    private String name;
    private String region;
    private String country;
    private String lat;
    private String lon;
    private String localtime;
}
