package com.example.weather_api.service;

import com.example.weather_api.model.Weather;
import com.example.weather_api.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final OpenWeatherApi openWeatherApi;

    public String getWeather(String city, String country) {
        LocalDateTime thresholdTime = LocalDateTime.now().minusHours(1);

        return weatherRepository.findByCityAndCountry(city, country)
                .map(w -> refreshIfExpired(w, thresholdTime))
                .orElseGet(() -> fetchAndSave(city, country));
    }

    private String refreshIfExpired(Weather weather, LocalDateTime thresholdTime) {
        if (weather.getLastUpdated().isBefore(thresholdTime)) {
            String description = openWeatherApi.getWeather(weather.getCity(), weather.getCountry());
            weather.setDescription(description);
            weather.setLastUpdated(LocalDateTime.now());
            weatherRepository.save(weather);

            log.info("Weather refreshed: {}\nCity: {}\nCountry: {}\nAcquired from OpenWeather API",
                    description, weather.getCity(), weather.getCountry());
            return description;
        }

        log.info("Weather cached: {}\nCity: {}\nCountry: {}\nAcquired from database",
                weather.getDescription(), weather.getCity(), weather.getCountry());
        return weather.getDescription();
    }

    private String fetchAndSave(String city, String country) {
        String description = openWeatherApi.getWeather(city, country);
        Weather weather = Weather.builder()
                .city(city)
                .country(country)
                .description(description)
                .lastUpdated(LocalDateTime.now())
                .build();
        weatherRepository.save(weather);

        log.info("Weather new fetch: {}\nCity: {}\nCountry: {}\nAcquired from OpenWeather API",
                description, city, country);
        return description;
    }
}
