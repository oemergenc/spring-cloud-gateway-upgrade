package com.gateway.configuration;

import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.Metrics;

@Configuration
public class TracingConfiguration {

  public TracingConfiguration(ObservationRegistry observationRegistry) {
    Metrics.observationRegistry(observationRegistry);
  }

  @Bean
  public ContextSnapshotFactory contextSnapshotFactory() {
    return ContextSnapshotFactory.builder().build();
  }
}
