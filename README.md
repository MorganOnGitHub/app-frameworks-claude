## Missing Functionality
GraphQL not correctly implemented, missing GraphQLController, Query and Mutation Mappings.

## Full Prompt Verbatim

I need you to build a Spring Boot application exposing a RESTful API that manages planets and moons.

The entities of this system are the following:

Planet
• planet_id (Primary Key)
• name
• type (e.g., terrestrial, gas giant)
• radius_km
• mass_kg
• orbital_period_days
Moon
• moon_id (Primary Key)
• name
• diameter_km
• orbital_period_days
• planet_id (Foreign Key to Planet)
User (for Security)
• user_id (Primary Key)
• username (unique)
• password (hashed)
• role (enum: ADMIN, STAFF, STUDENT)
• Optional: enabled, timestamps for creation/update

Use the following sample data to facilitate  the testing of the application in Swagger and POSTMAN:
-- Sample Data
('Mercury', 'terrestrial', 2439.7, 3.3011e23, 87.97),
('Venus', 'terrestrial', 6051.8, 4.8675e24, 224.70),
('Earth', 'terrestrial', 6371.0, 5.972e24, 365.26),
('Mars', 'terrestrial', 3389.5, 6.4171e23, 686.98),
('Jupiter', 'gas giant', 69911.0, 1.8982e27, 4332.59),
('Saturn', 'gas giant', 58232.0, 5.6834e26, 10759.22),
('Uranus', 'ice giant', 25362.0, 8.6810e25, 30688.5),
('Neptune', 'ice giant', 24622.0, 1.02413e26, 60182.0);


-- Earth's moon
('Moon', 3474.8, 27.32, 3),

-- Mars's moons
('Phobos', 22.2, 0.319, 4),
('Deimos', 12.6, 1.263, 4),

-- Jupiter's major moons
('Io', 3643.2, 1.769, 5),
('Europa', 3121.6, 3.551, 5),
('Ganymede', 5268.2, 7.155, 5),
('Callisto', 4820.6, 16.689, 5),

-- Saturn's major moons
('Titan', 5149.5, 15.945, 6),
('Rhea', 1527.0, 4.518, 6),
('Iapetus', 1469.0, 79.330, 6),
('Dione', 1123.0, 2.737, 6),
('Tethys', 1062.0, 1.888, 6),
('Enceladus', 504.2, 1.370, 6),
('Mimas', 396.4, 0.942, 6),

-- Uranus's major moons
('Titania', 1577.8, 8.706, 7),
('Oberon', 1522.8, 13.463, 7),
('Umbriel', 1169.4, 4.144, 7),
('Ariel', 1157.8, 2.520, 7),
('Miranda', 471.6, 1.413, 7),

-- Neptune's major moons
('Triton', 2706.8, 5.877, 8),
('Proteus', 420.0, 1.122, 8);

-- users
('admin','$2a$12$TyZyF8qlubhLg13tQjUum.burhvBMzyX.J1GUcUHlOqn6MOWSvxXS',true, true, 'ADMIN'), -- password1
('john', '$2a$12$TyZyF8qlubhLg13tQjUum.burhvBMzyX.J1GUcUHlOqn6MOWSvxXS',true, true, 'STAFF'),
('amy', '$2a$12$TyZyF8qlubhLg13tQjUum.burhvBMzyX.J1GUcUHlOqn6MOWSvxXS',true, true, 'STUDENT'),
('maria', '$2a$12$TyZyF8qlubhLg13tQjUum.burhvBMzyX.J1GUcUHlOqn6MOWSvxXS', true, true, 'STAFF'),
('lee', '$2a$12$TyZyF8qlubhLg13tQjUum.burhvBMzyX.J1GUcUHlOqn6MOWSvxXS', false, false, 'STUDENT');

Expose the following API endpoints as part of the system:

REST
Planets
• Add a new planet to the database.
• Retrieve all planets.
• Retrieve a planet by its unique ID.
• Update the details of an existing planet (e.g., change its mass).
• Remove a planet from the database by its unique ID.
• Retrieve planets based on their type (e.g., gas giant, terrestrial).
1 You may use a GenAI tool to generate this sample data for the manual version as well as for the GenAI version.
• Retrieve specific fields of a planet (e.g., only name and mass_kg for use in other parts
of the application).

Moons
• Add a new moon to the database, ensuring the planet exists
• Retrieve a list of all moons.
• Retrieve a moon by its unique ID.
• Remove a moon from the database by its unique ID.
• List moons by planet name (i.e., retrieve all moons for a specific planet).
• Count the number of moons for a specific planet.

GraphQL
User
• Find user by ID (Query)
• Create user (Mutation)

For Security in the application, I need you to implement basic authentication for the roles provided.
Admins have full access to all endpoints including user management(GraphQL).
Staff can create, update, delete planets and moons.
Students only have read access to planets and moons.
Passwords are to be securely hashed.
Secure all endpoints with the appropriate check of the role.

Furthermore, implement separation of concerns(AOP).
Centralise exception handling, handling security centrally and demonstrate 3 logging pointcuts from AspectJ.

Data that comes from the request body as Json should be validated and handled centrally. Validate using Jakarta on the DTOs.
Lastly, Use Spring Boot's best practices, api docs via Swagger and Meaning logging/error responses.


