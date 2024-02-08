package com.gateway.configuration;

import io.micrometer.context.ContextSnapshotFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {

  private ContextSnapshotFactory contextSnapshotFactory;

  public ServerConfiguration(ContextSnapshotFactory contextSnapshotFactory) {
    this.contextSnapshotFactory = contextSnapshotFactory;
  }

  @Bean
  public NettyServerCustomizer defaultNettyServerCustomizer() {
    return server ->
        server
            .accessLog(true)
            .doOnConnection(
                connection -> {
                  connection.addHandlerLast(tracingChannelDuplexHandler());
                });
  }

  private TracingChannelDuplexHandler tracingChannelDuplexHandler() {
    return new TracingChannelDuplexHandler(contextSnapshotFactory);
  }
}
