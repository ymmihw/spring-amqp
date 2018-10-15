package com.ymmihw.spring.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
    Producer producer = ctx.getBean(Producer.class);
    producer.sendMessages("Hello, world!");
    Thread.sleep(1000);
  }
}
