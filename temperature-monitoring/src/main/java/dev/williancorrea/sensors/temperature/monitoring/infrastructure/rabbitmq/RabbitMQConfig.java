package dev.williancorrea.sensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String EXCHANGE = "temperature-processing.temperature-received.v1.e";
  public static final String QUEUE_PROCESSING_TEMPERATURE = "temperature-monitoring.process-temperature.v1.q";
  public static final String QUEUE_ALERTING = "temperature-monitoring.alerting.v1.q";

  @Bean
  public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
  }
  
  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  public FanoutExchange exchange() {
    return ExchangeBuilder
        .fanoutExchange(EXCHANGE)
        .build();
  }

  

  @Bean
  public Queue queueProcessingTemperature() {
    return QueueBuilder
        .durable(QUEUE_PROCESSING_TEMPERATURE)
        .build();
  }
  
  @Bean
  public Binding bindingProcessingTemperature() {
    return BindingBuilder
        .bind(queueProcessingTemperature())
        .to(exchange());
  }


  @Bean
  public Queue queueAlerting() {
    return QueueBuilder
        .durable(QUEUE_ALERTING)
        .build();
  }

  @Bean
  public Binding bindingAlerting() {
    return BindingBuilder
        .bind(queueAlerting())
        .to(exchange());
  }

}
