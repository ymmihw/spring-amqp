package com.ymmihw.spring.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessages(String message) {
    rabbitTemplate.convertAndSend(Config.FANOUT_EXCHANGE_NAME, "", message);
    rabbitTemplate.convertAndSend(Config.TOPIC_EXCHANGE_NAME, "user.not-important.info", message);
    rabbitTemplate.convertAndSend(Config.TOPIC_EXCHANGE_NAME, "user.important.error", message);
  }
}
