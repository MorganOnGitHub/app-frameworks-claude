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
public class MoonSummaryDTO {

    private Long moonId;

    @NotBlank(message = "Moon name is required")
    private String name;

    @NotNull(message = "Diameter is required")
    @Positive(message = "Diameter must be positive")
    private Double diameterKm;

    @NotNull(message = "Planet ID is required")
    @Positive(message = "Planet ID must be positive")
    private Long planetId;
}