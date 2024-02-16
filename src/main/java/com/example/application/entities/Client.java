package com.example.application.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientid", nullable = false, unique = true)
    private Integer clientid;

    @NotBlank(message = "Full name is required")
    @Column(name = "fullname", nullable = false, unique = true, length = 50)
    private String fullname;

    @NotBlank(message = "Address is required")
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;

    @NotBlank(message = "E-mail is required")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
}