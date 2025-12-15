package dev.williancorrea.sensors.temperature.monitoring.infrastructure.rabbitmq;

import java.util.Map;
import dev.williancorrea.sensors.temperature.monitoring.api.model.TemperatureLogData;
import dev.williancorrea.sensors.temperature.monitoring.domain.service.SensorAlertService;
import dev.williancorrea.sensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

  private final TemperatureMonitoringService temperatureMonitoringService;
  private final SensorAlertService sensorAlertService;

  @RabbitListener(queues = RabbitMQConfig.QUEUE_PROCESSING_TEMPERATURE, concurrency = "2-3")
  public void habdleProcesssingTemperature(@Payload TemperatureLogData temperatureLogData,
                                           @Headers Map<String, Object> headers) {
    TSID sensorID = temperatureLogData.getSensorId();
    Double temperature = temperatureLogData.getValue();
    log.info("Temperature updated: SensorId {}, Temperature {}", sensorID, temperature);
    log.info("Headers: SensorId {}", headers.get("sensorId"));

    temperatureMonitoringService.processTemperatureReading(temperatureLogData);
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_PROCESSING_TEMPERATURE, concurrency = "2-3")
  public void habdleAlerting(@Payload TemperatureLogData temperatureLogData) {
    sensorAlertService.handleAlert(temperatureLogData);
  }
}
