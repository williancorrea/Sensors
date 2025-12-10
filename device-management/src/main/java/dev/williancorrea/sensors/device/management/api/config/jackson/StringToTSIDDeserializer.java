package dev.williancorrea.sensors.device.management.api.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import io.hypersistence.tsid.TSID;

public class StringToTSIDDeserializer extends JsonDeserializer<TSID> {
  @Override
  public TSID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return TSID.from(p.getText());
  }
}
