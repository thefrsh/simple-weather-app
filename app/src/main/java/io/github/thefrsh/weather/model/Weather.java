package io.github.thefrsh.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather
{
    private String main;
    private String description;
    private Double temperature;
    private Double feelsLikeTemperature;
    private Integer pressure;
    private Double windSpeed;
}
