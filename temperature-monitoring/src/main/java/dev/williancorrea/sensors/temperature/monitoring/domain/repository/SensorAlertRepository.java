package dev.williancorrea.sensors.temperature.monitoring.domain.repository;

import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorAlert;
import dev.williancorrea.sensors.temperature.monitoring.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorId> {
}
