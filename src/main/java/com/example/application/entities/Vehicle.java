    package com.example.application.entities;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    // Vehicle class (parent)
    @MappedSuperclass
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Vehicle {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "vehicleid", nullable = false, unique = true)
        private Integer vehicleid;

        @NotNull(message = "Body type is required")
        @Column(name = "bodytype", nullable = false, length = 50)
        private String bodytype;

        @NotNull(message = "Fuel type is required")
        @Column(name = "fueltype", nullable = false, length = 50)
        private String fueltype;

        @NotNull(message = "Transmission is required")
        @Column(name = "transmission", nullable = false, length = 50)
        private String transmission;
    }
