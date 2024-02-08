package com.gateway.configuration;

import io.micrometer.context.ContextSnapshotFactory;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

  private final ContextSnapshotFactory contextSnapshotFactory;

  public ClientConfiguration(ContextSnapshotFactory contextSnapshotFactory) {
    this.contextSnapshotFactory = contextSnapshotFactory;
  }

  @Bean
  public ReactorNettyHttpClientMapper reactorNettyHttpClientMapper() {
    return httpClient ->
        httpClient.doOnConnected(
            connection -> {
              connection.addHandlerLast(tracingChannelDuplexHandler());
            });
  }

  @Bean
  public HttpClientCustomizer httpClientCustomizer(ContextSnapshotFactory contextSnapshotFactory) {
    return httpClient ->
        httpClient.doOnConnected(
            connection -> {
              connection.addHandlerLast(tracingChannelDuplexHandler());
            });
  }

  @Bean
  public WebClient webClient(WebClient.Builder webClientBuilder) {
    return webClientBuilder.build();
  }

  private TracingChannelDuplexHandler tracingChannelDuplexHandler() {
    return new TracingChannelDuplexHandler(contextSnapshotFactory);
  }
}
