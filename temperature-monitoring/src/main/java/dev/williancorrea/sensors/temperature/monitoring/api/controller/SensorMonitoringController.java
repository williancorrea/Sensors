package dev.williancorrea.sensors.temperature.monitoring.api.controller;

import java.time.Duration;
import dev.williancorrea.sensors.temperature.monitoring.api.model.SensorMonitoringOutput;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import dev.williancorrea.sensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

  private final SensorMonitoringRepository sensorMonitoringRepository;

  @GetMapping
  public SensorMonitoringOutput getDetail(@PathVariable TSID sensorId) {
    var sensorMonitoring = findOrDefault(sensorId);
    return new SensorMonitoringOutput(sensorMonitoring);
  }


  @PutMapping("/enable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void enable(@PathVariable TSID sensorId) {
    var sensorMonitoring = findOrDefault(sensorId);
    if (Boolean.TRUE.equals(sensorMonitoring.getEnabled())) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Sensor already enabled");
    }
    sensorMonitoring.setEnabled(true);
    sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
  }

  @DeleteMapping("/enable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @SneakyThrows
  public void disable(@PathVariable TSID sensorId) {
    var sensorMonitoring = findOrDefault(sensorId);
    if (Boolean.FALSE.equals(sensorMonitoring.getEnabled())) {
      Thread.sleep(Duration.ofSeconds(10));
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Sensor already disabled");
    }

    sensorMonitoring.setEnabled(false);
    sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
  }

  private SensorMonitoring findOrDefault(TSID sensorId) {
    return sensorMonitoringRepository.findById(new SensorId(sensorId))
        .orElse(SensorMonitoring.builder()
            .id(new SensorId(sensorId))
            .enabled(false)
            .lastTemperature(null)
            .updatedAt(null)
            .build());
  }
}
