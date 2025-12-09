package dev.williancorrea.sensors.temperature.monitoring.api.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.TemperatureLog;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureLogOutput {

  private UUID id;
  private TSID sensorID;
  private OffsetDateTime registeredAt;
  private Double value;

  public TemperatureLogOutput(TemperatureLog temperatureLog) {
    this.id = temperatureLog.getId().getValue();
    this.sensorID = temperatureLog.getSensorId().getValue();
    this.registeredAt = temperatureLog.getRegisteredAt();
    this.value = temperatureLog.getValue();
  }
}
