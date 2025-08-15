package com.example.weather_api.service;

import com.example.weather_api.model.Weather;
import com.example.weather_api.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final OpenWeatherApi openWeatherApi;

    public String getWeather() {
        String city = "london";
        String country = "uk";

        return weatherRepository.findByCityAndCountry(city, country)
                .map(w -> {
                    String description = w.getDescription();
                    log.info("Weather description: {}\nCity: {}\nCountry: {}\nAcquired from database", description, city, country);
                    return description;
                })
                .orElseGet(() -> {
                    String description = openWeatherApi.getWeather(city, country);
                    log.info("Weather description: {}\nCity: {}\nCountry: {}\nAcquired from open weather API", description, city, country);
                    saveWeather(city, country, description);
                    return description;
                });
    }

    private void saveWeather(String city, String country, String description) {
        Weather weather = Weather.builder()
                .city(city)
                .country(country)
                .description(description)
                .build();
        weatherRepository.save(weather);
    }
}
