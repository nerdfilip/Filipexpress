package com.example.application.repositories;

import com.example.application.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    @Query("SELECT COUNT(r) > 0 FROM Rent r WHERE r.car.vehicleid = :vehicleid")
    boolean existsByVehicleId(@Param("vehicleid") Integer vehicleId);

    List<Rent> findByClientFullnameContainingIgnoreCase(String clientName);
}

