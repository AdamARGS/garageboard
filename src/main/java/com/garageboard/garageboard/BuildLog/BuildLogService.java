package com.garageboard.garageboard.BuildLog;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.garageboard.garageboard.Car.Car;

@Service
public class BuildLogService {

    BuildLogRepository buildLogRepository;

    public BuildLogService(BuildLogRepository buildLogRepository) {
        this.buildLogRepository = buildLogRepository;
    }

    public BuildLog addBuildLog(Car car, String content) {
        BuildLog buildLog = new BuildLog();

        buildLog.setCar(car);
        buildLog.setContent(content);

        return buildLogRepository.save(buildLog);
    }

    public List<BuildLogResponseDTO> getBuildLogs(Car car) {
        List<BuildLog> logs = buildLogRepository.findByCar(car);
        return logs.stream()
                .map(BuildLogResponseDTO::new)
                .collect(Collectors.toList());
    }

    public BuildLog findById(long id) {
        return buildLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Build log not found."));
    }

    public void deleteBuildLog(BuildLog buildLog) {
        buildLogRepository.delete(buildLog);
    }

    public BuildLogResponseDTO updateBuildLog(BuildLog buildLog, Map<String, String> body) {
        if (body.get("content") != null)
            buildLog.setContent(body.get("content"));

        return new BuildLogResponseDTO(buildLogRepository.save(buildLog));
    }
}
