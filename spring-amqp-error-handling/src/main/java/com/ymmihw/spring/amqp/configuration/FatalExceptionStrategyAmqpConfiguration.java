package com.ymmihw.spring.amqp.configuration;

import static com.ymmihw.spring.amqp.configuration.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;
import static com.ymmihw.spring.amqp.configuration.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;
import com.ymmihw.spring.amqp.errorhandler.CustomFatalExceptionStrategy;

@Configuration
@ConditionalOnProperty(value = "amqp.configuration.current", havingValue = "fatal-error-strategy")
public class FatalExceptionStrategyAmqpConfiguration {

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory,
      SimpleRabbitListenerContainerFactoryConfigurer configurer) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setErrorHandler(errorHandler());
    return factory;
  }

  @Bean
  public ErrorHandler errorHandler() {
    return new ConditionalRejectingErrorHandler(customExceptionStrategy());
  }

  @Bean
  FatalExceptionStrategy customExceptionStrategy() {
    return new CustomFatalExceptionStrategy();
  }

  @Bean
  Queue messagesQueue() {
    return QueueBuilder.durable(QUEUE_MESSAGES).build();
  }

  @Bean
  DirectExchange messagesExchange() {
    return new DirectExchange(EXCHANGE_MESSAGES);
  }

  @Bean
  Binding bindingMessages() {
    return BindingBuilder.bind(messagesQueue()).to(messagesExchange()).with(QUEUE_MESSAGES);
  }
}
