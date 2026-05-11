package com.garageboard.garageboard.Car;

import java.util.List;
import java.util.Map;
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

    public CarResponseDTO updateCar(Car car, Map<String, String> body) {
        if (body.get("year") != null)
            car.setYear(Integer.parseInt(body.get("year")));
        if (body.get("make") != null)
            car.setMake(body.get("make"));
        if (body.get("model") != null)
            car.setModel(body.get("model"));
        if (body.get("trim") != null)
            car.setTrim(body.get("trim"));
        if (body.get("description") != null)
            car.setDescription(body.get("description"));

        return new CarResponseDTO(carRepository.save(car));
    }
}
