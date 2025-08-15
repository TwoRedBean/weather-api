package com.example.weather_api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {

    private final OpenWeatherApi openWeatherApi;

    public String getWeather() {
        return openWeatherApi.getWeather();
    }
}
