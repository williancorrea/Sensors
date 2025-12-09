package dev.williancorrea.sensors.device.management.domain.model;

import java.io.Serializable;
import java.util.Objects;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SensorId implements Serializable {

  private static final long serialVersionUID = -221765741468968596L;

  private TSID value;

  public SensorId(TSID value) {
    Objects.requireNonNull(value);
    this.value = value;
  }

  public SensorId(Long value) {
    Objects.requireNonNull(value);
    this.value = TSID.from(value);
  }

  public SensorId(String value) {
    Objects.requireNonNull(value);
    this.value = TSID.from(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
