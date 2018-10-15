package com.ymmihw.spring.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumers {
  private static final Logger logger = LoggerFactory.getLogger(Consumers.class);

  @RabbitListener(queues = {Config.FANOUT_QUEUE1_NAME})
  public void receiveMessageFromFanout1(String message) {
    logger.info("Received fanout 1 message: " + message);
  }

  @RabbitListener(queues = {Config.FANOUT_QUEUE2_NAME})
  public void receiveMessageFromFanout2(String message) {
    logger.info("Received fanout 2 message: " + message);
  }

  @RabbitListener(queues = {Config.TOPIC_QUEUE1_NAME})
  public void receiveMessageFromTopic1(String message) {
    logger.info("Received topic 1 message: " + message);
  }

  @RabbitListener(queues = {Config.TOPIC_QUEUE2_NAME})
  public void receiveMessageFromTopic2(String message) {
    logger.info("Received topic 2 message: " + message);
  }
}
