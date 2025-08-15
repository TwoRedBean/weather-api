package com.example.weather_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper = new ObjectMapper();

    public String getWeather() {
        String url = String.format("%s?q=%s&appid=%s", baseUrl, "London,uk", apiKey);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);
            return root.get("weather").get(0).get("description").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get weather from weather API: ", e);
        }
    }
}
