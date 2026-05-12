package com.garageboard.garageboard.BuildLog;

import java.util.Map;

import org.springframework.http.HttpStatus;
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

import com.garageboard.garageboard.Car.Car;
import com.garageboard.garageboard.Car.CarService;
import com.garageboard.garageboard.User.User;

@RestController
@RequestMapping("/api/builds")
public class BuildLogController {

    private final BuildLogService buildLogService;
    private final CarService carService;

    public BuildLogController(BuildLogService buildLogService, CarService carService) {
        this.buildLogService = buildLogService;
        this.carService = carService;
    }

    @PostMapping("/car/{carId}/add")
    public ResponseEntity<?> addBuildLog(@PathVariable long carId, @RequestBody Map<String, String> body) {
        try {
            Car car = carService.findById(carId);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (carService.userOwnsCar(user, car)) {
                BuildLog buildLog = buildLogService.addBuildLog(car, body.get("content"));
                return ResponseEntity.ok(new BuildLogResponseDTO(buildLog));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not own this car.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<?> viewBuildLogs(@PathVariable long carId) {
        try {
            Car car = carService.findById(carId);
            return ResponseEntity.ok(buildLogService.getBuildLogs(car));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuildLog(@PathVariable long id) {
        try {
            BuildLog buildLog = buildLogService.findById(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getId().equals(buildLog.getCar().getUser().getId())) {
                buildLogService.deleteBuildLog(buildLog);
                return ResponseEntity.ok("Build log with ID " + id + " deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete another user's build log!");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBuildLog(@PathVariable long id, @RequestBody Map<String, String> body) {
        try {
            BuildLog buildLog = buildLogService.findById(id);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getId().equals(buildLog.getCar().getUser().getId())) {
                return ResponseEntity.ok(buildLogService.updateBuildLog(buildLog, body));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot edit another user's build log!");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}