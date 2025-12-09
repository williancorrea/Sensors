package dev.williancorrea.sensors.device.management.common;

import java.util.Optional;
import io.hypersistence.tsid.TSID;

public class IdGenerator {

  private static final TSID.Factory factory;

  static {
    Optional.ofNullable(System.getenv("tsid.node"))
        .ifPresent(tsidNode -> System.setProperty("tsid.node", tsidNode));

    Optional.ofNullable(System.getenv("tsid.node.count"))
        .ifPresent(tsidNodeCount -> System.setProperty("tsid.node.count", tsidNodeCount));

    factory = TSID.Factory.builder().build();
  }

  private IdGenerator() {
  }

  public static TSID generateTSID() {
    return factory.generate();
  }
  
}
