package com.space.planetmoonapi.service;

import com.space.planetmoonapi.dto.PlanetDTO;
import com.space.planetmoonapi.dto.PlanetSummaryDTO;
import com.space.planetmoonapi.entity.Planet;
import com.space.planetmoonapi.exception.DuplicateResourceException;
import com.space.planetmoonapi.exception.ResourceNotFoundException;
import com.space.planetmoonapi.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlanetService {

    private final PlanetRepository planetRepository;

    @Transactional
    public PlanetDTO createPlanet(PlanetDTO planetDTO) {
        log.info("Creating new planet: {}", planetDTO.getName());

        if (planetRepository.findByName(planetDTO.getName()).isPresent()) {
            throw new DuplicateResourceException("Planet with name '" + planetDTO.getName() + "' already exists");
        }

        Planet planet = new Planet();
        planet.setName(planetDTO.getName());
        planet.setType(planetDTO.getType());
        planet.setRadiusKm(planetDTO.getRadiusKm());
        planet.setMassKg(planetDTO.getMassKg());
        planet.setOrbitalPeriodDays(planetDTO.getOrbitalPeriodDays());

        Planet savedPlanet = planetRepository.save(planet);
        log.info("Planet created successfully with ID: {}", savedPlanet.getPlanetId());

        return convertToDTO(savedPlanet);
    }

    public List<PlanetDTO> getAllPlanets() {
        log.info("Fetching all planets");
        return planetRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PlanetDTO getPlanetById(Long id) {
        log.info("Fetching planet by ID: {}", id);
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + id));
        return convertToDTO(planet);
    }

    @Transactional
    public PlanetDTO updatePlanet(Long id, PlanetDTO planetDTO) {
        log.info("Updating planet with ID: {}", id);

        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + id));

        if (!planet.getName().equals(planetDTO.getName()) &&
                planetRepository.findByName(planetDTO.getName()).isPresent()) {
            throw new DuplicateResourceException("Planet with name '" + planetDTO.getName() + "' already exists");
        }

        planet.setName(planetDTO.getName());
        planet.setType(planetDTO.getType());
        planet.setRadiusKm(planetDTO.getRadiusKm());
        planet.setMassKg(planetDTO.getMassKg());
        planet.setOrbitalPeriodDays(planetDTO.getOrbitalPeriodDays());

        Planet updatedPlanet = planetRepository.save(planet);
        log.info("Planet updated successfully with ID: {}", updatedPlanet.getPlanetId());

        return convertToDTO(updatedPlanet);
    }

    @Transactional
    public void deletePlanet(Long id) {
        log.info("Deleting planet with ID: {}", id);

        if (!planetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Planet not found with ID: " + id);
        }

        planetRepository.deleteById(id);
        log.info("Planet deleted successfully with ID: {}", id);
    }

    public List<PlanetDTO> getPlanetsByType(String type) {
        log.info("Fetching planets by type: {}", type);
        return planetRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PlanetSummaryDTO> getAllPlanetSummaries() {
        log.info("Fetching all planet summaries");
        return planetRepository.findAllPlanetSummaries();
    }

    public PlanetSummaryDTO getPlanetSummaryById(Long id) {
        log.info("Fetching planet summary by ID: {}", id);
        return planetRepository.findPlanetSummaryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + id));
    }

    private PlanetDTO convertToDTO(Planet planet) {
        PlanetDTO dto = new PlanetDTO();
        dto.setPlanetId(planet.getPlanetId());
        dto.setName(planet.getName());
        dto.setType(planet.getType());
        dto.setRadiusKm(planet.getRadiusKm());
        dto.setMassKg(planet.getMassKg());
        dto.setOrbitalPeriodDays(planet.getOrbitalPeriodDays());
        return dto;
    }
}