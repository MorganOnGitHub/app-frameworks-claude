package com.space.planetmoonapi.controller;

import com.space.planetmoonapi.dto.PlanetDTO;
import com.space.planetmoonapi.dto.PlanetSummaryDTO;
import com.space.planetmoonapi.service.PlanetService;
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

import java.util.List;

@RestController
@RequestMapping("/api/planets")
@RequiredArgsConstructor
@Tag(name = "Planets", description = "Planet management APIs")
@SecurityRequirement(name = "basicAuth")
public class PlanetController {

    private final PlanetService planetService;

    @Operation(summary = "Create a new planet", description = "Add a new planet to the database. Requires ADMIN or STAFF role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Planet created successfully",
                    content = @Content(schema = @Schema(implementation = PlanetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Planet with this name already exists"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    public ResponseEntity<PlanetDTO> createPlanet(@Valid @RequestBody PlanetDTO planetDTO) {
        PlanetDTO createdPlanet = planetService.createPlanet(planetDTO);
        return new ResponseEntity<>(createdPlanet, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all planets", description = "Retrieve a list of all planets")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public ResponseEntity<List<PlanetDTO>> getAllPlanets() {
        List<PlanetDTO> planets = planetService.getAllPlanets();
        return ResponseEntity.ok(planets);
    }

    @Operation(summary = "Get planet by ID", description = "Retrieve a planet by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planet found",
                    content = @Content(schema = @Schema(implementation = PlanetDTO.class))),
            @ApiResponse(responseCode = "404", description = "Planet not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanetDTO> getPlanetById(
            @Parameter(description = "ID of the planet to retrieve") @PathVariable Long id) {
        PlanetDTO planet = planetService.getPlanetById(id);
        return ResponseEntity.ok(planet);
    }

    @Operation(summary = "Update a planet", description = "Update the details of an existing planet. Requires ADMIN or STAFF role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planet updated successfully"),
            @ApiResponse(responseCode = "404", description = "Planet not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlanetDTO> updatePlanet(
            @Parameter(description = "ID of the planet to update") @PathVariable Long id,
            @Valid @RequestBody PlanetDTO planetDTO) {
        PlanetDTO updatedPlanet = planetService.updatePlanet(id, planetDTO);
        return ResponseEntity.ok(updatedPlanet);
    }

    @Operation(summary = "Delete a planet", description = "Remove a planet from the database. Requires ADMIN or STAFF role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Planet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Planet not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanet(
            @Parameter(description = "ID of the planet to delete") @PathVariable Long id) {
        planetService.deletePlanet(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get planets by type", description = "Retrieve planets filtered by their type (e.g., terrestrial, gas giant)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<PlanetDTO>> getPlanetsByType(
            @Parameter(description = "Type of planets to retrieve") @PathVariable String type) {
        List<PlanetDTO> planets = planetService.getPlanetsByType(type);
        return ResponseEntity.ok(planets);
    }

    @Operation(summary = "Get all planet summaries", description = "Retrieve specific fields (name and mass) of all planets")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved summaries")
    @GetMapping("/summaries")
    public ResponseEntity<List<PlanetSummaryDTO>> getAllPlanetSummaries() {
        List<PlanetSummaryDTO> summaries = planetService.getAllPlanetSummaries();
        return ResponseEntity.ok(summaries);
    }

    @Operation(summary = "Get planet summary by ID", description = "Retrieve specific fields (name and mass) of a planet by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Summary found"),
            @ApiResponse(responseCode = "404", description = "Planet not found")
    })
    @GetMapping("/{id}/summary")
    public ResponseEntity<PlanetSummaryDTO> getPlanetSummaryById(
            @Parameter(description = "ID of the planet") @PathVariable Long id) {
        PlanetSummaryDTO summary = planetService.getPlanetSummaryById(id);
        return ResponseEntity.ok(summary);
    }
}