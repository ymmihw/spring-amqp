package com.ymmihw.spring.amqp.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ymmihw.spring.amqp.configuration.SimpleDLQAmqpConfiguration;
import com.ymmihw.spring.amqp.errorhandler.BusinessException;

@Configuration
public class MessagesConsumer {
  public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";


  private static final Logger log = LoggerFactory.getLogger(MessagesConsumer.class);
  private final RabbitTemplate rabbitTemplate;

  public MessagesConsumer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @RabbitListener(queues = SimpleDLQAmqpConfiguration.QUEUE_MESSAGES)
  public void receiveMessage(Message message) throws BusinessException {
    log.info("Received message: {}", message.toString());
    throw new BusinessException();
  }

  @Bean
  @ConditionalOnProperty(value = "amqp.configuration.current", havingValue = "simple-dlq")
  public SimpleDLQAmqpContainer simpleAmqpContainer() {
    return new SimpleDLQAmqpContainer(rabbitTemplate);
  }

  @Bean
  @ConditionalOnProperty(value = "amqp.configuration.current", havingValue = "routing-dlq")
  public RoutingDLQAmqpContainer routingDLQAmqpContainer() {
    return new RoutingDLQAmqpContainer(rabbitTemplate);
  }

  @Bean
  @ConditionalOnProperty(value = "amqp.configuration.current", havingValue = "dlx-custom")
  public DLQCustomAmqpContainer dlqAmqpContainer() {
    return new DLQCustomAmqpContainer(rabbitTemplate);
  }

  @Bean
  @ConditionalOnProperty(value = "amqp.configuration.current", havingValue = "parking-lot-dlx")
  public ParkingLotDLQAmqpContainer parkingLotDLQAmqpContainer() {
    return new ParkingLotDLQAmqpContainer(rabbitTemplate);
  }
}
