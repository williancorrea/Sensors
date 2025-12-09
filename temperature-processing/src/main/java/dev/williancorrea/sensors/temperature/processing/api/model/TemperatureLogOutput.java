package dev.williancorrea.sensors.temperature.processing.api.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemperatureLogOutput {
  
  private UUID id;
  private TSID sensorID;
  private OffsetDateTime registeredAt;
  private Double value;
}
