package com.space.planetmoonapi.config;

import com.space.planetmoonapi.entity.Moon;
import com.space.planetmoonapi.entity.Planet;
import com.space.planetmoonapi.entity.User;
import com.space.planetmoonapi.enums.UserRole;
import com.space.planetmoonapi.repository.MoonRepository;
import com.space.planetmoonapi.repository.PlanetRepository;
import com.space.planetmoonapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final PlanetRepository planetRepository;
    private final MoonRepository moonRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("Starting data initialization...");

        // Initialize Users
        initializeUsers();

        // Initialize Planets
        initializePlanets();

        // Initialize Moons
        initializeMoons();

        log.info("Data initialization completed successfully!");
    }

    private void initializeUsers() {
        log.info("Initializing users...");

        // Password: password1 (same for all users as per requirement)
        String encodedPassword = "$2a$12$TyZyF8qlubhLg13tQjUum.burhvBMzyX.J1GUcUHlOqn6MOWSvxXS";

        userRepository.save(new User("admin", encodedPassword, true, true, UserRole.ADMIN));
        userRepository.save(new User("john", encodedPassword, true, true, UserRole.STAFF));
        userRepository.save(new User("amy", encodedPassword, true, true, UserRole.STUDENT));
        userRepository.save(new User("maria", encodedPassword, true, true, UserRole.STAFF));
        userRepository.save(new User("lee", encodedPassword, false, false, UserRole.STUDENT));

        log.info("Initialized {} users", userRepository.count());
    }

    private void initializePlanets() {
        log.info("Initializing planets...");

        planetRepository.save(new Planet("Mercury", "terrestrial", 2439.7, 3.3011e23, 87.97));
        planetRepository.save(new Planet("Venus", "terrestrial", 6051.8, 4.8675e24, 224.70));
        planetRepository.save(new Planet("Earth", "terrestrial", 6371.0, 5.972e24, 365.26));
        planetRepository.save(new Planet("Mars", "terrestrial", 3389.5, 6.4171e23, 686.98));
        planetRepository.save(new Planet("Jupiter", "gas giant", 69911.0, 1.8982e27, 4332.59));
        planetRepository.save(new Planet("Saturn", "gas giant", 58232.0, 5.6834e26, 10759.22));
        planetRepository.save(new Planet("Uranus", "ice giant", 25362.0, 8.6810e25, 30688.5));
        planetRepository.save(new Planet("Neptune", "ice giant", 24622.0, 1.02413e26, 60182.0));

        log.info("Initialized {} planets", planetRepository.count());
    }

    private void initializeMoons() {
        log.info("Initializing moons...");

        Planet earth = planetRepository.findByName("Earth").orElseThrow();
        Planet mars = planetRepository.findByName("Mars").orElseThrow();
        Planet jupiter = planetRepository.findByName("Jupiter").orElseThrow();
        Planet saturn = planetRepository.findByName("Saturn").orElseThrow();
        Planet uranus = planetRepository.findByName("Uranus").orElseThrow();
        Planet neptune = planetRepository.findByName("Neptune").orElseThrow();

        // Earth's moon
        moonRepository.save(new Moon("Moon", 3474.8, 27.32, earth));

        // Mars's moons
        moonRepository.save(new Moon("Phobos", 22.2, 0.319, mars));
        moonRepository.save(new Moon("Deimos", 12.6, 1.263, mars));

        // Jupiter's major moons
        moonRepository.save(new Moon("Io", 3643.2, 1.769, jupiter));
        moonRepository.save(new Moon("Europa", 3121.6, 3.551, jupiter));
        moonRepository.save(new Moon("Ganymede", 5268.2, 7.155, jupiter));
        moonRepository.save(new Moon("Callisto", 4820.6, 16.689, jupiter));

        // Saturn's major moons
        moonRepository.save(new Moon("Titan", 5149.5, 15.945, saturn));
        moonRepository.save(new Moon("Rhea", 1527.0, 4.518, saturn));
        moonRepository.save(new Moon("Iapetus", 1469.0, 79.330, saturn));
        moonRepository.save(new Moon("Dione", 1123.0, 2.737, saturn));
        moonRepository.save(new Moon("Tethys", 1062.0, 1.888, saturn));
        moonRepository.save(new Moon("Enceladus", 504.2, 1.370, saturn));
        moonRepository.save(new Moon("Mimas", 396.4, 0.942, saturn));

        // Uranus's major moons
        moonRepository.save(new Moon("Titania", 1577.8, 8.706, uranus));
        moonRepository.save(new Moon("Oberon", 1522.8, 13.463, uranus));
        moonRepository.save(new Moon("Umbriel", 1169.4, 4.144, uranus));
        moonRepository.save(new Moon("Ariel", 1157.8, 2.520, uranus));
        moonRepository.save(new Moon("Miranda", 471.6, 1.413, uranus));

        // Neptune's major moons
        moonRepository.save(new Moon("Triton", 2706.8, 5.877, neptune));
        moonRepository.save(new Moon("Proteus", 420.0, 1.122, neptune));

        log.info("Initialized {} moons", moonRepository.count());
    }
}