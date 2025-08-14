package com.example.weather_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherApi {

    @Value("${app.api-key}")
    private String apiKey;
    @Value("${app.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeather() {
        String url = String.format("%s?q=%s&appid=%s", baseUrl, "London,uk", apiKey);
        return restTemplate.getForObject(url, String.class);
    }
}
