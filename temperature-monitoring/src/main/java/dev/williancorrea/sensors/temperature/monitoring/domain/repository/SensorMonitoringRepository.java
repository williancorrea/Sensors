package dev.williancorrea.sensors.temperature.monitoring.domain.repository;

import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {
}
