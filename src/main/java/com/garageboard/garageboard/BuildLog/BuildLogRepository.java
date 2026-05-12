package com.garageboard.garageboard.BuildLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garageboard.garageboard.Car.Car;

@Repository
public interface BuildLogRepository extends JpaRepository<BuildLog, Long> {
    List<BuildLog> findByCar(Car car);
}
