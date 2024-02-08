package com.gateway;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ItTest extends AbstractTestBase {

  @ParameterizedTest
  //    @ValueSource(strings = { "/question-route", "/question-controller" })
  @ValueSource(strings = {"/question-controller"})
  void test(String path) {
    sendRequest(path);
    assertThat(appender.list)
        .filteredOn(event -> Objects.equals(event.getLoggerName(), filterLogger.getName()))
        .hasSize(1)
        .singleElement()
        .matches(
            event -> event.getMDCPropertyMap().containsKey("traceId"),
            "expected controller log to contain traceId")
        .matches(
            event -> event.getMDCPropertyMap().containsKey("spanId"),
            "expected controller log to contain spanId");
  }
}
