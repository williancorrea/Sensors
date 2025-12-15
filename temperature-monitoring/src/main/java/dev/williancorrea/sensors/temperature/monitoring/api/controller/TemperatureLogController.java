package dev.williancorrea.sensors.temperature.monitoring.api.controller;

import dev.williancorrea.sensors.temperature.monitoring.api.model.TemperatureLogData;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import dev.williancorrea.sensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
@RequiredArgsConstructor
public class TemperatureLogController {
  
  private final TemperatureLogRepository temperatureLogRepository;
  
  @GetMapping
  public Page<TemperatureLogData> search(@PathVariable("sensorId") TSID sensorId,
                                         @PageableDefault Pageable pageable) {
    return temperatureLogRepository.findAllBySensorId(new SensorId(sensorId), pageable)
        .map(TemperatureLogData::new);
  }
}
