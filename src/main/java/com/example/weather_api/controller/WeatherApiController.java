package com.example.weather_api.controller;

import com.example.weather_api.service.OpenWeatherApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/weathers")
@AllArgsConstructor
public class WeatherApiController {

    private final OpenWeatherApi openWeatherApi;

    @GetMapping
    public String getWeather() {
        return openWeatherApi.getWeather();
    }
}
