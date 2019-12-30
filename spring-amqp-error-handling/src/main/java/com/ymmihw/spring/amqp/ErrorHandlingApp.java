package com.ymmihw.spring.amqp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.ymmihw.spring.amqp.producer.MessageProducer;

@SpringBootApplication
@EnableScheduling
public class ErrorHandlingApp {

  @Autowired
  MessageProducer messageProducer;

  public static void main(String[] args) {
    SpringApplication.run(ErrorHandlingApp.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    messageProducer.sendMessage();
  }
}
