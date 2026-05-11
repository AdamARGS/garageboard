package com.garageboard.garageboard.Car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarResponseDTO {
    private long id;
    private int year;
    private String make;
    private String model;

    public CarResponseDTO(Car car) {
        this.id = car.getId(); // useful while in dev, might remove in production
        this.year = car.getYear();
        this.make = car.getMake();
        this.model = car.getModel();
    }
}
