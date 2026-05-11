package com.garageboard.garageboard.dto;

import com.garageboard.garageboard.entities.Car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarResponseDTO {
    private int year;
    private String make;
    private String model;

    public CarResponseDTO(Car car) {
        this.year = car.getYear();
        this.make = car.getMake();
        this.model = car.getModel();
    }
}
