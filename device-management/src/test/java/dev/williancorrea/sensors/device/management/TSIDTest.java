package dev.williancorrea.sensors.device.management;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import io.hypersistence.tsid.TSID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TSIDTest {

  @Test
  void shouldGenerateTSID() {
    TSID tsid = IdGenerator.generateTSID();
    Assertions.assertThat(tsid.getInstant()).isCloseTo(Instant.now(), Assertions.within(1, ChronoUnit.MINUTES));
  }
}
