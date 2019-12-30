package com.ymmihw.spring.amqp;

import org.testcontainers.containers.RabbitMQContainer;

public class MyRabbitMQContainer extends RabbitMQContainer {
  private static final String IMAGE_VERSION = "rabbitmq:3.8.2";
  private static MyRabbitMQContainer container;

  private MyRabbitMQContainer() {
    super(IMAGE_VERSION);
  }

  public static MyRabbitMQContainer getInstance() {
    if (container == null) {
      container = new MyRabbitMQContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.addExposedPort(6379);
    super.start();
    System.setProperty("RABBITMQ_HOST", container.getContainerIpAddress());
    System.setProperty("RABBITMQ_PORT", String.valueOf(container.getFirstMappedPort()));
  }


  @Override
  public void stop() {
    // do nothing, JVM handles shut down
  }
}
