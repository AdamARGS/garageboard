package com.garageboard.garageboard.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garageboard.garageboard.dto.CarResponseDTO;
import com.garageboard.garageboard.entities.Car;
import com.garageboard.garageboard.entities.User;
import com.garageboard.garageboard.services.CarService;

@RestController
@RequestMapping("/api/users/cars")
public class CarController {

    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody Map<String, String> body) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Car car = carService.addCar(
                    user,
                    Integer.parseInt(body.get("year")),
                    body.get("make"),
                    body.get("model"),
                    body.get("trim"),
                    body.get("description"));
            return ResponseEntity.ok(new CarResponseDTO(car));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
