package com.space.planetmoonapi.repository;

import com.space.planetmoonapi.dto.MoonSummaryDTO;
import com.space.planetmoonapi.entity.Moon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoonRepository extends JpaRepository<Moon, Long> {

    Optional<Moon> findByName(String name);

    List<Moon> findByPlanetPlanetId(Long planetId);

    @Query("SELECT new com.space.planetmoonapi.dto.MoonSummaryDTO(m.moonId, m.name, m.diameterKm, m.planet.planetId) FROM Moon m")
    List<MoonSummaryDTO> findAllMoonSummaries();

    @Query("SELECT new com.space.planetmoonapi.dto.MoonSummaryDTO(m.moonId, m.name, m.diameterKm, m.planet.planetId) FROM Moon m WHERE m.moonId = :id")
    Optional<MoonSummaryDTO> findMoonSummaryById(@Param("id") Long id);

    @Query("SELECT new com.space.planetmoonapi.dto.MoonSummaryDTO(m.moonId, m.name, m.diameterKm, m.planet.planetId) FROM Moon m WHERE m.planet.planetId = :planetId")
    List<MoonSummaryDTO> findMoonSummariesByPlanetId(@Param("planetId") Long planetId);

    @Query("SELECT m FROM Moon m WHERE m.planet.name = :planetName")
    List<Moon> findMoonsByPlanetName(@Param("planetName") String planetName);

    @Query("SELECT COUNT(m) FROM Moon m WHERE m.planet.planetId = :planetId")
    Long countMoonsByPlanetId(@Param("planetId") Long planetId);

    @Query("SELECT COUNT(m) FROM Moon m WHERE m.planet.name = :planetName")
    Long countMoonsByPlanetName(@Param("planetName") String planetName);
}