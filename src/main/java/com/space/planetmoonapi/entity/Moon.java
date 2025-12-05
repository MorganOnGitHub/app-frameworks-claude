package com.space.planetmoonapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moon_id")
    private Long moonId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "diameter_km", nullable = false)
    private Double diameterKm;

    @Column(name = "orbital_period_days", nullable = false)
    private Double orbitalPeriodDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planet_id", nullable = false)
    @JsonBackReference
    private Planet planet;

    public Moon(String name, Double diameterKm, Double orbitalPeriodDays, Planet planet) {
        this.name = name;
        this.diameterKm = diameterKm;
        this.orbitalPeriodDays = orbitalPeriodDays;
        this.planet = planet;
    }
}