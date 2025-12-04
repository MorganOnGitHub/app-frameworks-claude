package com.space.planetmoonapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetSummaryDTO {
    private String name;
    private Double massKg;
}