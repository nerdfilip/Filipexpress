package com.example.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

// Car class with specific fields
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car extends Vehicle {

    @NotBlank(message = "Car brand is required")
    @Column(name = "carbrand", nullable = false, length = 50)
    private String carbrand;

    @NotBlank(message = "Car model is required")
    @Column(name = "carmodel", nullable = false, length = 50)
    private String carmodel;

    @NotNull(message = "Manufacturing year is required")
    @Min(value = 1885, message = "Manufacturing year must be greater than or equal to 1885")
    @Column(name = "manufacturingyear", nullable = false)
    private Integer manufacturingyear;

    @NotNull(message = "Engine capacity is required")
    @Min(value = 0, message = "Engine capacity must be greater than or equal to 0")
    @Column(name = "enginecapacity", nullable = false)
    private Double enginecapacity;

    @NotNull(message = "Seats is required")
    @Column(name = "seats", nullable = false)
    private Integer seats;

    // Constructor with super attributes
    public Car(Integer vehicleid, String bodytype, String fueltype, String transmission,
               String carbrand, String carmodel, Integer manufacturingyear, Double enginecapacity, Integer seats) {
        super(vehicleid, bodytype, fueltype, transmission);
        this.carbrand = carbrand;
        this.carmodel = carmodel;
        this.manufacturingyear = manufacturingyear;
        this.enginecapacity = enginecapacity;
        this.seats = seats;
    }
}
