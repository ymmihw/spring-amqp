package com.ymmihw.spring.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
    AmqpTemplate template = ctx.getBean(RabbitTemplate.class);
    template.convertAndSend("Hello, world!");
    Thread.sleep(1000);
  }
}
