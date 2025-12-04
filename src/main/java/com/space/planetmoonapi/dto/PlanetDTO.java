package com.space.planetmoonapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDTO {

    private Long planetId;

    @NotBlank(message = "Planet name is required")
    private String name;

    @NotBlank(message = "Planet type is required")
    private String type;

    @NotNull(message = "Radius is required")
    @Positive(message = "Radius must be positive")
    private Double radiusKm;

    @NotNull(message = "Mass is required")
    @Positive(message = "Mass must be positive")
    private Double massKg;

    @NotNull(message = "Orbital period is required")
    @Positive(message = "Orbital period must be positive")
    private Double orbitalPeriodDays;
}