package dev.williancorrea.sensors.temperature.processing.api.controller;

import java.time.OffsetDateTime;
import dev.williancorrea.sensors.temperature.processing.api.model.TemperatureLogOutput;
import dev.williancorrea.sensors.temperature.processing.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import lombok.extern.slf4j.Slf4j;
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
public class TemperatureProcessingController {

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
        .sensorID(sensorId)
        .value(temperature)
        .registeredAt(OffsetDateTime.now())
        .build();

    log.info(logOutput.toString());
  }
}
