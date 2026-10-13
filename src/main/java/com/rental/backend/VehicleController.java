package com.rental.backend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // READ: Get all vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // READ: Get one vehicle
    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    // CREATE: Add a vehicle
    @PostMapping
    @org.springframework.web.bind.annotation.ResponseStatus(
            HttpStatus.CREATED)
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        vehicle.setAvailable(true);
        return vehicleRepository.save(vehicle);
    }

    // UPDATE: Edit a vehicle
    @PutMapping("/{id}")
    public Vehicle updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle updatedVehicle) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vehicle not found"));

        vehicle.setName(updatedVehicle.getName());
        vehicle.setBrand(updatedVehicle.getBrand());
        vehicle.setPricePerDay(updatedVehicle.getPricePerDay());
        vehicle.setVehicleType(updatedVehicle.getVehicleType());
        vehicle.setImageUrl(updatedVehicle.getImageUrl());
        vehicle.setAvailable(updatedVehicle.isAvailable());

        return vehicleRepository.save(vehicle);
    }

    // DELETE: Remove a vehicle
    @DeleteMapping("/{id}")
    @org.springframework.web.bind.annotation.ResponseStatus(
            HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Vehicle not found");
        }

        vehicleRepository.deleteById(id);
    }
}
