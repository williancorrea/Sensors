package dev.williancorrea.sensors.device.management.api.controller;

import dev.williancorrea.sensors.device.management.api.model.SensorInput;
import dev.williancorrea.sensors.device.management.api.model.SensorOutput;
import dev.williancorrea.sensors.device.management.common.IdGenerator;
import dev.williancorrea.sensors.device.management.domain.model.Sensor;
import dev.williancorrea.sensors.device.management.domain.model.SensorId;
import dev.williancorrea.sensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

  private final SensorRepository sensorRepository;

  @GetMapping
  public Page<SensorOutput> findAll(@PageableDefault Pageable pageable) {
    return sensorRepository.findAll(pageable)
        .map(SensorOutput::new);
  }

  @GetMapping("/{sensorId}")
  public SensorOutput getOne(@PathVariable("sensorId") TSID sensorId) {
    Sensor sensor = findById(sensorId);
    return new SensorOutput(sensor);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SensorOutput create(@Valid @RequestBody SensorInput input) {
    Sensor sensor = Sensor.builder()
        .id(new SensorId(IdGenerator.generateTSID()))
        .name(input.getName())
        .ip(input.getIp())
        .location(input.getLocation())
        .protocol(input.getProtocol())
        .model(input.getModel())
        .enabled(false)
        .build();

    return new SensorOutput(sensorRepository.saveAndFlush(sensor));
  }

  @PutMapping("/{sensorId}")
  public SensorOutput update(@PathVariable("sensorId") TSID sensorId,
                             @Valid @RequestBody SensorInput input) {
    Sensor sensor = findById(sensorId);

    sensor.setName(input.getName());
    sensor.setIp(input.getIp());
    sensor.setLocation(input.getLocation());
    sensor.setProtocol(input.getProtocol());
    sensor.setModel(input.getModel());

    return new SensorOutput(sensorRepository.saveAndFlush(sensor));
  }

  @DeleteMapping("/{sensorId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable("sensorId") TSID sensorId) {
    Sensor sensor = findById(sensorId);
    sensorRepository.delete(sensor);
  }


  private Sensor findById(TSID sensorId) {
    return sensorRepository.findById(new SensorId(sensorId))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found"));
  }
}
