package com.example.application.services;

import com.example.application.entities.Car;
import com.example.application.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// CRUD Operations for Car & Vehicle classes
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Create
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    // Read
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> searchCarsByBrand(String brand) {
        return carRepository.searchCarsByBrand(brand);
    }

    // Delete
    public void deleteCar(Integer carId) {
        carRepository.deleteById(carId);
    }
}