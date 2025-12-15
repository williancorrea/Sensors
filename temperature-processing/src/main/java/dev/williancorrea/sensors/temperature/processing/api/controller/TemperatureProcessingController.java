package dev.williancorrea.sensors.temperature.processing.api.controller;

import static dev.williancorrea.sensors.temperature.processing.infrastructure.rabbitmq.RabbitMQConfig.FANOUT_EXCHANGE_NAME;

import java.time.OffsetDateTime;
import dev.williancorrea.sensors.temperature.processing.api.model.TemperatureLogOutput;
import dev.williancorrea.sensors.temperature.processing.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures/data")
@RequiredArgsConstructor
public class TemperatureProcessingController {

  private final RabbitTemplate rabbitTemplate;

  @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
  public void data(@PathVariable("sensorId") TSID sensorId,
                   @RequestBody String input) {
    if (input == null || input.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
    }

    double temperature;
    try {
      temperature = Double.parseDouble(input);
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
    }

    var logOutput = TemperatureLogOutput.builder()
        .id(IdGenerator.generateTimeBasedUUID())
        .sensorId(sensorId)
        .value(temperature)
        .registeredAt(OffsetDateTime.now())
        .build();

    log.info(logOutput.toString());

    String exchange = FANOUT_EXCHANGE_NAME;
    String routingKey = "";

    rabbitTemplate.convertAndSend(exchange, routingKey, logOutput, message -> {
      message.getMessageProperties()
          .setHeader("sensorId", logOutput.getSensorId().toString());

      return message;
    });
  }

}
