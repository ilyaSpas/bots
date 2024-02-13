package org.example.demotgbot.service;

import org.example.demotgbot.model.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    public Weather getWeather() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.weatherapi.com/v1/current.json?" +
                     "key=afd771ae1b8c41eb81b205809241901&" +
                     "q=Moscow&lang=ru";
        Weather weather = restTemplate.getForObject(url, Weather.class);
        return weather;
    }
}
