package com.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocketHandshakeException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.output.OutputFrame;

@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureObservability
class WebSocketIntegrationTest {

  protected static Stubby4jContainer stubby4jContainer = new Stubby4jContainer();
  protected static String websocketMockAddress;

  static {
    stubby4jContainer.start();
    websocketMockAddress =
        "ws://" + stubby4jContainer.getHost() + ":" + stubby4jContainer.getFirstMappedPort();
  }

  @AfterAll
  static void tearDown() {
    final String logs = stubby4jContainer.getLogs(OutputFrame.OutputType.STDOUT);
    System.out.println("STUBBY LOGS");
    System.out.println(logs);
  }

  @Test
  void webSocketCommunicationIsPossibleWithSession() {
    final WebSocketListener webSocketListener = new WebSocketListener();
    WebSocket ws =
        HttpClient.newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(getWsUri("/ws/demo/web-socket/1"), webSocketListener)
            .join();

    ws.sendText("hello_request", true);
    ws.sendText("disconnect_request", true);

    await().until(() -> webSocketListener.responses.size() == 3);
    assertThat(webSocketListener.responses)
        .containsExactly("successfully_connected", "hello_response", "disconnect_response");
    assertThat(ws.isInputClosed()).isTrue();
    assertThat(ws.isOutputClosed()).isTrue();
  }

  @Test
  void webSocketCommunicationWithoutSessionIsAnError() {
    final WebSocketListener webSocketListener = new WebSocketListener();

    final Throwable exception =
        assertThrows(
            CompletionException.class,
            () -> {
              HttpClient.newHttpClient()
                  .newWebSocketBuilder()
                  .buildAsync(
                      getWsUri("/ffp-websocket-service/ws/demo/web-socket/1"), webSocketListener)
                  .join();
            });
    assertThat(exception.getCause()).isInstanceOf(WebSocketHandshakeException.class);
  }

  private URI getWsUri(final String path) {
    return URI.create("ws://localhost:" + stubby4jContainer.getFirstMappedPort() + path);
  }

  private static class WebSocketListener implements WebSocket.Listener {
    public List<String> responses = new ArrayList<>();

    @Override
    public void onOpen(WebSocket webSocket) {
      WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
      responses.add(data.toString());
      return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
      WebSocket.Listener.super.onError(webSocket, error);
    }
  }
}
