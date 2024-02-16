package com.example.application.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

// Many-To-One with Vehicle and Client classes

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentid", nullable = false, unique = true)
    private Integer rentid;

    @ManyToOne
    @JoinColumn(name = "vehicleid", nullable = false, foreignKey = @ForeignKey(name = "vehicleid"))
    private Car car;

    @ManyToOne
    @JoinColumn(name = "clientid", nullable = false, foreignKey = @ForeignKey(name = "clientid"))
    private Client client;

    @NotNull(message = "Rental date is required")
    @Column(name = "rentaldate", nullable = false)
    private LocalDate rentaldate;

    @Column(name = "period", nullable = false)
    @Min(value = 1, message = "Period must be greater than or equal to 1")
    private Integer period;

    @Column(name = "rentprice", nullable = false)
    @Min(value = 1000, message = "Rent price must be greater than or equal to $1000")
    private Double rentprice;

    @Column(name = "rentpriceTVA", nullable = false)
    private Double rentpriceTVA;

    @Column(name = "fines")
    private Double fines;

    @Column(name = "total", nullable = false)
    private Double total;

    public void setRentprice(double rentprice) {
        this.rentprice = rentprice;

        // Calculate rentpriceTVA based on rentprice and 19% tax
        double taxRate = 0.19;
        this.rentpriceTVA = rentprice + (rentprice * taxRate);

        // Add to total
        updateTotal();
    }

    public void setFines(double fines) {
        this.fines = fines;

        // Update total based on rentpriceTVA and fines
        updateTotal();
    }

    private void updateTotal() {
        if(fines != null) this.total = rentpriceTVA + fines;
        else this.total = rentpriceTVA;
    }
}
