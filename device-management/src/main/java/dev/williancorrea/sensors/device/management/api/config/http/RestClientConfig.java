package dev.williancorrea.sensors.device.management.api.config.http;

import dev.williancorrea.sensors.device.management.api.config.http.client.SensorMonitoringClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

  @Bean
  public SensorMonitoringClient sensorMonitoringClient(RestClientFactory factory) {
    RestClient restClient = factory.temperatureMonitoringClient();

    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

    return proxyFactory.createClient(SensorMonitoringClient.class);
  }
}
