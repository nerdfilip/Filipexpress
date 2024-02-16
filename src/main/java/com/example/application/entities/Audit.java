package com.example.application.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditid", nullable = false, unique = true)
    private Integer auditid;

    @Column(name = "fullname", nullable = false, unique = true, length = 50)
    private String fullname;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "carbrand", nullable = false, length = 50)
    private String carbrand;

    @Column(name = "carmodel", nullable = false, length = 50)
    private String carmodel;

    @Column(name = "period", nullable = false)
    private Integer period;

    @Column(name = "rentaldate", nullable = false)
    private LocalDate rentaldate;

    @Column(name = "returndate")
    private LocalDate returnDate;

    @Column(name = "rentpriceTVA", nullable = false)
    private Double rentpriceTVA;

    @Column(name = "fines")
    private Double fines;

    @Column(name = "total", nullable = false)
    private Double total;
}
