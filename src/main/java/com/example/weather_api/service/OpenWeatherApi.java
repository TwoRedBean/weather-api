package com.example.weather_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OpenWeatherApi {

    @Value("${app.open-weather-api.api-key}")
    private String apiKey;
    @Value("${app.open-weather-api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getWeather(String city, String country) {
        String url = String.format("%s?q=%s,%s&appid=%s", baseUrl, city, country, apiKey);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);
            return root.get("weather").get(0).get("description").asText();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to get weather from weather API.");
        }
    }
}
