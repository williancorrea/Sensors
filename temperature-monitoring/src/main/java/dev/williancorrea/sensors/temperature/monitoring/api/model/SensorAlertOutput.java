package dev.williancorrea.sensors.temperature.monitoring.api.model;

import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorAlert;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorAlertOutput {

  private TSID id;
  private Double maxTemperature;
  private Double minTemperature;

  public SensorAlertOutput(SensorAlert sensorAlert) {
    this.id = sensorAlert.getId().getValue();
    this.maxTemperature = sensorAlert.getMaxTemperature();
    this.minTemperature = sensorAlert.getMinTemperature();
  }
}
