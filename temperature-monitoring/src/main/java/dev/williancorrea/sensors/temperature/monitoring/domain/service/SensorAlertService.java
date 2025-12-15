package dev.williancorrea.sensors.temperature.monitoring.domain.service;

import dev.williancorrea.sensors.temperature.monitoring.api.model.TemperatureLogData;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import dev.williancorrea.sensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorAlertService {

  private final SensorAlertRepository sensorAlertRepository;

  @Transactional
  public void handleAlert(TemperatureLogData temperatureLogData) {

    sensorAlertRepository.findById(new SensorId(temperatureLogData.getSensorId()))
        .ifPresentOrElse(sensorAlert -> {
          if (sensorAlert.getMaxTemperature() != null
              && temperatureLogData.getValue().compareTo(sensorAlert.getMaxTemperature()) >= 0) {
            log.info("Alert MAX temperature: SensorId {} Temp {}",
                temperatureLogData.getSensorId(),
                temperatureLogData.getValue());
          } else if (sensorAlert.getMinTemperature() != null
              && temperatureLogData.getValue().compareTo(sensorAlert.getMinTemperature()) <= 0) {
            log.info("Alert MIN temperature: SensorId {} Temp {}",
                temperatureLogData.getSensorId(),
                temperatureLogData.getValue());
          } else {
            logIgnoreAlert(temperatureLogData);
          }
        }, () -> logIgnoreAlert(temperatureLogData));

  }

  private static void logIgnoreAlert(TemperatureLogData temperatureLogData) {
    log.info("Alert ignored: Sensor alert not found for SensorId {}", temperatureLogData.getSensorId());
  }

}
