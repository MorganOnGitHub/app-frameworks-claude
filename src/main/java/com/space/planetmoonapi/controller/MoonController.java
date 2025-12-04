package com.space.planetmoonapi.controller;

import com.space.planetmoonapi.dto.MoonDTO;
import com.space.planetmoonapi.service.MoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/moons")
@RequiredArgsConstructor
@Tag(name = "Moons", description = "Moon management APIs")
@SecurityRequirement(name = "basicAuth")
public class MoonController {

    private final MoonService moonService;

    @Operation(summary = "Create a new moon", description = "Add a new moon to the database. Requires ADMIN or STAFF role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Moon created successfully",
                    content = @Content(schema = @Schema(implementation = MoonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Planet not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    public ResponseEntity<MoonDTO> createMoon(@Valid @RequestBody MoonDTO moonDTO) {
        MoonDTO createdMoon = moonService.createMoon(moonDTO);
        return new ResponseEntity<>(createdMoon, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all moons", description = "Retrieve a list of all moons")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public ResponseEntity<List<MoonDTO>> getAllMoons() {
        List<MoonDTO> moons = moonService.getAllMoons();
        return ResponseEntity.ok(moons);
    }

    @Operation(summary = "Get moon by ID", description = "Retrieve a moon by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moon found",
                    content = @Content(schema = @Schema(implementation = MoonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Moon not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MoonDTO> getMoonById(
            @Parameter(description = "ID of the moon to retrieve") @PathVariable Long id) {
        MoonDTO moon = moonService.getMoonById(id);
        return ResponseEntity.ok(moon);
    }

    @Operation(summary = "Delete a moon", description = "Remove a moon from the database. Requires ADMIN or STAFF role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Moon deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Moon not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoon(
            @Parameter(description = "ID of the moon to delete") @PathVariable Long id) {
        moonService.deleteMoon(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get moons by planet name", description = "Retrieve all moons for a specific planet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "404", description = "Planet not found")
    })
    @GetMapping("/planet/{planetName}")
    public ResponseEntity<List<MoonDTO>> getMoonsByPlanetName(
            @Parameter(description = "Name of the planet") @PathVariable String planetName) {
        List<MoonDTO> moons = moonService.getMoonsByPlanetName(planetName);
        return ResponseEntity.ok(moons);
    }

    @Operation(summary = "Count moons by planet name", description = "Get the number of moons for a specific planet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Planet not found")
    })
    @GetMapping("/planet/{planetName}/count")
    public ResponseEntity<Map<String, Object>> countMoonsByPlanetName(
            @Parameter(description = "Name of the planet") @PathVariable String planetName) {
        Long count = moonService.countMoonsByPlanetName(planetName);
        Map<String, Object> response = new HashMap<>();
        response.put("planetName", planetName);
        response.put("moonCount", count);
        return ResponseEntity.ok(response);
    }
}