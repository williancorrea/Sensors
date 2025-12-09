package dev.williancorrea.sensors.temperature.monitoring.api.controller;

import dev.williancorrea.sensors.temperature.monitoring.api.model.SensorAlertInput;
import dev.williancorrea.sensors.temperature.monitoring.api.model.SensorAlertOutput;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorAlert;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import dev.williancorrea.sensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {

  private final SensorAlertRepository sensorAlertRepository;

  @GetMapping
  public SensorAlertOutput getAlert(@PathVariable TSID sensorId) {
    return new SensorAlertOutput(
        this.findBySensorId(sensorId)
    );
  }

  @PutMapping
  public SensorAlertOutput updateAlert(@PathVariable TSID sensorId,
                                       @RequestBody SensorAlertInput alertInput) {
    SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
        .orElse(SensorAlert.builder()
            .id(new SensorId(sensorId))
            .build());

    sensorAlert.setMaxTemperature(alertInput.getMaxTemperature());
    sensorAlert.setMinTemperature(alertInput.getMinTemperature());

    return new SensorAlertOutput(
        sensorAlertRepository.saveAndFlush(sensorAlert)
    );
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAlert(@PathVariable TSID sensorId) {
    sensorAlertRepository.delete(this.findBySensorId(sensorId));
  }

  private SensorAlert findBySensorId(TSID sensorId) {
    return sensorAlertRepository.findById(new SensorId(sensorId))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found"));
  }

}
