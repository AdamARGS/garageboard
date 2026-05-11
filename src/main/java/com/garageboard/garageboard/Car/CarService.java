package com.garageboard.garageboard.Car;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.garageboard.garageboard.User.User;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(User owner, int year, String make, String model, String trim, String description) {
        Car car = new Car();

        car.setUser(owner);
        car.setYear(year);
        car.setMake(make);
        car.setModel(model);
        car.setTrim(trim); // may be null
        car.setDescription(description); // may be null

        return carRepository.save(car);
    }

    public List<CarResponseDTO> getCars(User user) {
        List<Car> cars = carRepository.findByUser(user);
        return cars.stream()
                .map(CarResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Car findById(long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found."));
    }

    public void deleteCar(Car car) {
        carRepository.delete(car);
    }
}
