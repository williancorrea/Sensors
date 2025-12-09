package dev.williancorrea.sensors.device.management.api.model;

import dev.williancorrea.sensors.device.management.domain.model.Sensor;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SensorOutput {
  private TSID id;
  private String name;
  private String ip;
  private String location;
  private String protocol;
  private String model;
  private Boolean enabled;

  public SensorOutput(Sensor sensor) {
    this.id = sensor.getId().getValue();
    this.name = sensor.getName();
    this.ip = sensor.getIp();
    this.location = sensor.getLocation();
    this.protocol = sensor.getProtocol();
    this.model = sensor.getModel();
    this.enabled = sensor.getEnabled();
  }
  
}
