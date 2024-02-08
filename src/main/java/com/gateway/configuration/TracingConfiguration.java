package com.gateway.configuration;

import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.contextpropagation.ObservationAwareSpanThreadLocalAccessor;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;
import reactor.netty.Metrics;

@Configuration
public class TracingConfiguration {

  private final ObservationRegistry observationRegistry;
  private final Tracer tracer;

  public TracingConfiguration(ObservationRegistry observationRegistry, Tracer tracer) {
    this.observationRegistry = observationRegistry;
    this.tracer = tracer;
  }

  @PostConstruct
  public void postConstruct() {
    Hooks.enableAutomaticContextPropagation();
    ContextRegistry.getInstance()
        .registerThreadLocalAccessor(new ObservationAwareSpanThreadLocalAccessor(tracer));
    ObservationThreadLocalAccessor.getInstance().setObservationRegistry(observationRegistry);
    Metrics.observationRegistry(observationRegistry);
  }

  @Bean
  public ContextSnapshotFactory contextSnapshotFactory() {
    return ContextSnapshotFactory.builder().build();
  }
}
