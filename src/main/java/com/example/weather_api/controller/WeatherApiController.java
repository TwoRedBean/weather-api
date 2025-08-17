package com.example.weather_api.controller;

import com.example.weather_api.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/weathers")
@AllArgsConstructor
public class WeatherApiController {

    private final WeatherService weatherService;

    @GetMapping("/cities/{city}/countries/{country}")
    public String getWeather(@PathVariable String city, @PathVariable String country) {
        return weatherService.getWeather(city, country);
    }
}
