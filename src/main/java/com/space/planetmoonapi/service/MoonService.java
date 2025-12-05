package com.space.planetmoonapi.service;

import com.space.planetmoonapi.dto.MoonDTO;
import com.space.planetmoonapi.dto.MoonSummaryDTO;
import com.space.planetmoonapi.entity.Moon;
import com.space.planetmoonapi.entity.Planet;
import com.space.planetmoonapi.exception.DuplicateResourceException;
import com.space.planetmoonapi.exception.ResourceNotFoundException;
import com.space.planetmoonapi.repository.MoonRepository;
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
public class MoonService {

    private final MoonRepository moonRepository;
    private final PlanetRepository planetRepository;

    @Transactional
    public MoonDTO createMoon(MoonDTO moonDTO) {
        log.info("Creating new moon: {}", moonDTO.getName());

        if (moonRepository.findByName(moonDTO.getName()).isPresent()) {
            throw new DuplicateResourceException("Moon with name '" + moonDTO.getName() + "' already exists");
        }

        Planet planet = planetRepository.findById(moonDTO.getPlanetId())
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + moonDTO.getPlanetId()));

        Moon moon = new Moon();
        moon.setName(moonDTO.getName());
        moon.setDiameterKm(moonDTO.getDiameterKm());
        moon.setOrbitalPeriodDays(moonDTO.getOrbitalPeriodDays());
        moon.setPlanet(planet);

        Moon savedMoon = moonRepository.save(moon);
        log.info("Moon created successfully with ID: {}", savedMoon.getMoonId());

        return convertToDTO(savedMoon);
    }

    public List<MoonDTO> getAllMoons() {
        log.info("Fetching all moons");
        return moonRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MoonDTO getMoonById(Long id) {
        log.info("Fetching moon by ID: {}", id);
        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found with ID: " + id));
        return convertToDTO(moon);
    }

    @Transactional
    public MoonDTO updateMoon(Long id, MoonDTO moonDTO) {
        log.info("Updating moon with ID: {}", id);

        Moon moon = moonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found with ID: " + id));

        if (!moon.getName().equals(moonDTO.getName()) &&
                moonRepository.findByName(moonDTO.getName()).isPresent()) {
            throw new DuplicateResourceException("Moon with name '" + moonDTO.getName() + "' already exists");
        }

        Planet planet = planetRepository.findById(moonDTO.getPlanetId())
                .orElseThrow(() -> new ResourceNotFoundException("Planet not found with ID: " + moonDTO.getPlanetId()));

        moon.setName(moonDTO.getName());
        moon.setDiameterKm(moonDTO.getDiameterKm());
        moon.setOrbitalPeriodDays(moonDTO.getOrbitalPeriodDays());
        moon.setPlanet(planet);

        Moon updatedMoon = moonRepository.save(moon);
        log.info("Moon updated successfully with ID: {}", updatedMoon.getMoonId());

        return convertToDTO(updatedMoon);
    }

    @Transactional
    public void deleteMoon(Long id) {
        log.info("Deleting moon with ID: {}", id);

        if (!moonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Moon not found with ID: " + id);
        }

        moonRepository.deleteById(id);
        log.info("Moon deleted successfully with ID: {}", id);
    }

    public List<MoonDTO> getMoonsByPlanetId(Long planetId) {
        log.info("Fetching moons by planet ID: {}", planetId);

        if (!planetRepository.existsById(planetId)) {
            throw new ResourceNotFoundException("Planet not found with ID: " + planetId);
        }

        return moonRepository.findByPlanetPlanetId(planetId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MoonDTO> getMoonsByPlanetName(String planetName) {
        log.info("Fetching moons by planet name: {}", planetName);

        if (planetRepository.findByName(planetName).isEmpty()) {
            throw new ResourceNotFoundException("Planet not found with name: " + planetName);
        }

        return moonRepository.findMoonsByPlanetName(planetName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long countMoonsByPlanetId(Long planetId) {
        log.info("Counting moons for planet ID: {}", planetId);

        if (!planetRepository.existsById(planetId)) {
            throw new ResourceNotFoundException("Planet not found with ID: " + planetId);
        }

        return moonRepository.countMoonsByPlanetId(planetId);
    }

    public Long countMoonsByPlanetName(String planetName) {
        log.info("Counting moons for planet name: {}", planetName);

        if (planetRepository.findByName(planetName).isEmpty()) {
            throw new ResourceNotFoundException("Planet not found with name: " + planetName);
        }

        return moonRepository.countMoonsByPlanetName(planetName);
    }

    public List<MoonSummaryDTO> getAllMoonSummaries() {
        log.info("Fetching all moon summaries");
        return moonRepository.findAllMoonSummaries();
    }

    public MoonSummaryDTO getMoonSummaryById(Long id) {
        log.info("Fetching moon summary by ID: {}", id);
        return moonRepository.findMoonSummaryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moon not found with ID: " + id));
    }

    public List<MoonSummaryDTO> getMoonSummariesByPlanetId(Long planetId) {
        log.info("Fetching moon summaries by planet ID: {}", planetId);

        if (!planetRepository.existsById(planetId)) {
            throw new ResourceNotFoundException("Planet not found with ID: " + planetId);
        }

        return moonRepository.findMoonSummariesByPlanetId(planetId);
    }

    private MoonDTO convertToDTO(Moon moon) {
        MoonDTO dto = new MoonDTO();
        dto.setMoonId(moon.getMoonId());
        dto.setName(moon.getName());
        dto.setDiameterKm(moon.getDiameterKm());
        dto.setOrbitalPeriodDays(moon.getOrbitalPeriodDays());
        dto.setPlanetId(moon.getPlanet().getPlanetId());
        return dto;
    }
}