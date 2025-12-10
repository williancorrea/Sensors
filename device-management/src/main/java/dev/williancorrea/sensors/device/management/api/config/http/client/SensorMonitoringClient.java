package dev.williancorrea.sensors.device.management.api.config.http.client;

import dev.williancorrea.sensors.device.management.api.model.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {
  
  void enableMonitoring(TSID sensorId);
  void disableMonitoring(TSID sensorId);
  SensorMonitoringOutput getDetail(TSID sensorId);
}
