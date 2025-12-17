package dev.williancorrea.sensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
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

  public static final String PROCESSING_TEMPERATURE = "temperature-monitoring.process-temperature.v1";
  public static final String QUEUE_PROCESSING_TEMPERATURE = PROCESSING_TEMPERATURE + ".q";
  public static final String DEAD_LETTER_QUEUE_PROCESSING_TEMPERATURE = PROCESSING_TEMPERATURE + ".dlq";

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
    var args = new HashMap<String, Object>();
    args.put("x-dead-letter-exchange", "");
    args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_PROCESSING_TEMPERATURE);

    return QueueBuilder
        .durable(QUEUE_PROCESSING_TEMPERATURE)
        .withArguments(args)
        .build();
  }

  @Bean
  public Queue deadLetterQueueProcessingTemperature() {
    return QueueBuilder
        .durable(DEAD_LETTER_QUEUE_PROCESSING_TEMPERATURE)
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
