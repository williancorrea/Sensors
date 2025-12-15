package dev.williancorrea.sensors.temperature.processing.api.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import io.hypersistence.tsid.TSID;

public class TSIDToStringSerializer extends JsonSerializer<TSID> {

  @Override
  public void serialize(TSID value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.toString());
  }
}