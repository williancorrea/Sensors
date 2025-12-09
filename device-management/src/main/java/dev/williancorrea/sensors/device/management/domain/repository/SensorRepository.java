package dev.williancorrea.sensors.device.management.domain.repository;

import dev.williancorrea.sensors.device.management.domain.model.Sensor;
import dev.williancorrea.sensors.device.management.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}
