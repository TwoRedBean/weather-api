package com.example.weather_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weathers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_date_time", nullable = false, updatable = false, insertable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDateTime;
}
