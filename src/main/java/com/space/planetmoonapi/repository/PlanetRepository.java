package com.space.planetmoonapi.repository;

import com.space.planetmoonapi.dto.PlanetSummaryDTO;
import com.space.planetmoonapi.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    List<Planet> findByType(String type);

    Optional<Planet> findByName(String name);

    @Query("SELECT new com.space.planetmoonapi.dto.PlanetSummaryDTO(p.name, p.massKg) FROM Planet p")
    List<PlanetSummaryDTO> findAllPlanetSummaries();

    @Query("SELECT new com.space.planetmoonapi.dto.PlanetSummaryDTO(p.name, p.massKg) FROM Planet p WHERE p.planetId = :id")
    Optional<PlanetSummaryDTO> findPlanetSummaryById(@Param("id") Long id);
}