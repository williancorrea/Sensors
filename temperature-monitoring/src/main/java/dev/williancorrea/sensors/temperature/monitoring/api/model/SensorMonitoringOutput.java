package dev.williancorrea.sensors.temperature.monitoring.api.model;

import java.time.OffsetDateTime;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorMonitoringOutput {

  private TSID id;
  private Double lastTemperature;
  private OffsetDateTime updatedAt;
  private Boolean enabled;

  public SensorMonitoringOutput(SensorMonitoring sensorMonitoring) {
    this.id = sensorMonitoring.getId().getValue();
    this.lastTemperature = sensorMonitoring.getLastTemperature();
    this.updatedAt = sensorMonitoring.getUpdatedAt();
    this.enabled = sensorMonitoring.getEnabled();
  }
}
