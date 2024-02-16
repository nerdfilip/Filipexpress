package com.example.application.repositories;

import com.example.application.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE LOWER(c.carbrand) LIKE LOWER(CONCAT('%', :brand, '%'))")
    List<Car> searchCarsByBrand(@Param("brand") String brand);
}
