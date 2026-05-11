package com.garageboard.garageboard.Car;

import java.util.Map;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garageboard.garageboard.User.User;

@RestController
@RequestMapping("/api/users/cars")
public class CarController {

    CarService carService;
    CarRepository carRepository;

    public CarController(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
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

    @GetMapping("/viewCollection")
    public ResponseEntity<?> viewCollection() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(carService.getCars(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable long id) {
        try {
            Car car = carService.findById(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getId().equals(car.getUser().getId())) {
                carService.deleteCar(car);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete another user's car!");
            }
            return ResponseEntity.ok("Car with ID " + id + " deleted succesfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * // Putting this off for now
     * 
     * @PutMapping("/{id}")
     * public ResponseEntity<?> updateCar(@PathVariable long id, @RequestBody User
     * user, @RequestBody int year, @RequestBody String make, @RequestBody String
     * model, @RequestBody String trim, @RequestBody String description) {}
     */
}