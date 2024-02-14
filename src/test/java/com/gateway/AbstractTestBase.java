package com.gateway;

import static org.apache.http.HttpStatus.SC_OK;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.gateway.filter.LoggingFilter;
import io.restassured.RestAssured;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.springtest.MockServerTest;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@AutoConfigureObservability
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockServerTest("MOCK_BACKEND=http://localhost:${mockServerPort}")
public class AbstractTestBase {
  private static final String ACCESS_LOG_LOGGER_NAME = "reactor.netty.http.server.AccessLog";

  private MockServerClient mockBackend;
  ListAppender<ILoggingEvent> appender;

  Logger filterLogger = (Logger) LoggerFactory.getLogger(LoggingFilter.class);
  Logger accessLogLogger = (Logger) LoggerFactory.getLogger(ACCESS_LOG_LOGGER_NAME);

  @LocalServerPort Integer serverPort = -1;

  @BeforeEach
  public void setup() {
    appender = new ListAppender();
    appender.start();
    filterLogger.addAppender(appender);
    accessLogLogger.addAppender(appender);
  }

  @AfterEach
  public void tearDownLogAppender() {
    filterLogger.detachAppender(appender);
    accessLogLogger.detachAppender(appender);
  }

  HttpRequest sendRequest(String path) {
    mockBackendRespondOK();
    RestAssured.given().when().port(serverPort).get(path).then().statusCode(SC_OK);
    return sentRequest();
  }

  HttpRequest sendRequestAndTimeout(String path, int timeoutMs) {
    mockBackendRespondOK(timeoutMs);
    RestAssured.given().when().port(serverPort).get(path).then().statusCode(SC_OK);
    return sentRequest();
  }

  private void mockBackendRespondOK() {
    mockBackendRespondOK(0);
  }

  private void mockBackendRespondOK(int timeoutMs) {
    mockBackend
        .when(request())
        .respond(response().withDelay(TimeUnit.SECONDS, timeoutMs).withStatusCode(SC_OK));
  }

  private HttpRequest sentRequest() {
    final HttpRequest[] httpRequests = mockBackend.retrieveRecordedRequests(null);
    return httpRequests[httpRequests.length - 1];
  }
}
