package com.space.planetmoonapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planet_id")
    private Long planetId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "radius_km", nullable = false)
    private Double radiusKm;

    @Column(name = "mass_kg", nullable = false)
    private Double massKg;

    @Column(name = "orbital_period_days", nullable = false)
    private Double orbitalPeriodDays;

    @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Moon> moons = new ArrayList<>();

    public Planet(String name, String type, Double radiusKm, Double massKg, Double orbitalPeriodDays) {
        this.name = name;
        this.type = type;
        this.radiusKm = radiusKm;
        this.massKg = massKg;
        this.orbitalPeriodDays = orbitalPeriodDays;
    }
}