package dev.williancorrea.sensors.temperature.monitoring.domain.service;

import dev.williancorrea.sensors.temperature.monitoring.api.model.TemperatureLogData;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.TemperatureLog;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.TemperatureLogId;
import dev.williancorrea.sensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import dev.williancorrea.sensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureMonitoringService {

  private final SensorMonitoringRepository sensorMonitoringRepository;
  private final TemperatureLogRepository temperatureLogRepository;


  @Transactional
  public void processTemperatureReading(TemperatureLogData temperatureLogData) {
    sensorMonitoringRepository.findById(new SensorId(temperatureLogData.getSensorId()))
        .ifPresentOrElse(
            sensor -> handleSensorMonitoring(temperatureLogData, sensor),
            () -> logIgnoredTemperature(temperatureLogData)
        );
  }

  private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
    if (sensor.isEnabled()) {
      sensor.setLastTemperature(temperatureLogData.getValue());
      sensor.setUpdatedAt(temperatureLogData.getRegisteredAt());
      sensorMonitoringRepository.save(sensor);

      var temperatureLog = TemperatureLog.builder()
          .id(new TemperatureLogId(temperatureLogData.getId()))
          .registeredAt(temperatureLogData.getRegisteredAt())
          .value(temperatureLogData.getValue())
          .sensorId(new SensorId(temperatureLogData.getSensorId()))
          .build();
      temperatureLogRepository.save(temperatureLog);
      log.info("Temperature Updated: SensorId {}, Temperature {}",
          temperatureLogData.getSensorId(),
          temperatureLogData.getValue());
    } else {
      logIgnoredTemperature(temperatureLogData);
    }
  }

  private void logIgnoredTemperature(TemperatureLogData temperatureLogData) {
    log.info("Temperature ignored: SensorId {}, Temperature {}",
        temperatureLogData.getSensorId(),
        temperatureLogData.getValue());
  }
}
