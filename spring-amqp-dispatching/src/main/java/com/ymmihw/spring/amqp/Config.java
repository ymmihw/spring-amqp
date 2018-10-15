package com.ymmihw.spring.amqp;

import java.util.Arrays;
import java.util.List;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class Config {

  public static final String FANOUT_QUEUE1_NAME = "com.ymmihw.spring.amqp.fanout.queue1";
  public static final String FANOUT_QUEUE2_NAME = "com.ymmihw.spring.amqp.fanout.queue2";
  public static final String FANOUT_EXCHANGE_NAME = "com.ymmihw.spring.amqp.fanout.exchange";

  public static final String TOPIC_QUEUE1_NAME = "com.ymmihw.spring.amqp.topic.queue1";
  public static final String TOPIC_QUEUE2_NAME = "com.ymmihw.spring.amqp.topic.queue2";
  public static final String TOPIC_EXCHANGE_NAME = "com.ymmihw.spring.amqp.topic.exchange";

  @Bean
  public List<Declarable> topicBindings() {
    Queue topicQueue1 = new Queue(TOPIC_QUEUE1_NAME, false);
    Queue topicQueue2 = new Queue(TOPIC_QUEUE2_NAME, false);

    TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);

    return Arrays.asList(topicQueue1, topicQueue2, topicExchange,
        BindingBuilder.bind(topicQueue1).to(topicExchange).with("*.important.*"),
        BindingBuilder.bind(topicQueue2).to(topicExchange).with("user.#"));
  }

  @Bean
  public List<Declarable> fanoutBindings() {
    Queue fanoutQueue1 = new Queue(FANOUT_QUEUE1_NAME, false);
    Queue fanoutQueue2 = new Queue(FANOUT_QUEUE2_NAME, false);

    FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME);

    return Arrays.asList(fanoutQueue1, fanoutQueue2, fanoutExchange,
        BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
        BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
  }

  @Bean
  public SimpleRabbitListenerContainerFactory container(ConnectionFactory connectionFactory,
      SimpleRabbitListenerContainerFactoryConfigurer configurer) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    return factory;
  }

}
