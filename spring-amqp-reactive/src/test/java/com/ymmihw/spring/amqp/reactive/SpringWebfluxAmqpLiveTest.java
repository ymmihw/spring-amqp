package com.ymmihw.spring.amqp.reactive;


import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.ymmihw.spring.amqp.MyRabbitMQContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = SpringWebfluxAmqpApplication.class)
public class SpringWebfluxAmqpLiveTest {

  @ClassRule
  public static MyRabbitMQContainer container = MyRabbitMQContainer.getInstance();

  @LocalServerPort
  private int port;

  @Test
  public void whenSendingAMessageToQueue_thenAcceptedReturnCode() {

    WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

    client.post().uri("/queue/NYSE").bodyValue("Test Message").exchange().expectStatus()
        .isAccepted();

  }
}
