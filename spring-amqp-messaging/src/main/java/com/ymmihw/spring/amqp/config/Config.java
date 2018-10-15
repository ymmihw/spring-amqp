package com.ymmihw.spring.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ymmihw.spring.amqp.consumer.Consumer;

@Configuration
public class Config {
  private static final String MY_QUEUE = "myQueue";
  private static final String MY_EXCHANGE = "myExchange";

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    return connectionFactory;
  }

  @Bean
  public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate amqpTemplate = new RabbitTemplate();
    amqpTemplate.setConnectionFactory(connectionFactory);
    amqpTemplate.setExchange(MY_EXCHANGE);
    amqpTemplate.setRoutingKey("foo.bar");
    return amqpTemplate;
  }

  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    return rabbitAdmin;
  }

  @Bean
  public Queue queue() {
    Queue queue = new Queue(MY_QUEUE);
    return queue;
  }

  @Bean
  public TopicExchange topicExchange() {
    TopicExchange topicExchange = new TopicExchange(MY_EXCHANGE);
    return topicExchange;
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("foo.*");
  }

  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer(
      ConnectionFactory connectionFactory, Consumer messageListener) {

    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(MY_QUEUE);
    container.setMessageListener(messageListener);
    return container;
  }
}

