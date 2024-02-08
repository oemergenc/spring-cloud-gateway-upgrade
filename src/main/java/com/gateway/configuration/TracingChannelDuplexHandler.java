package com.gateway.configuration;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshotFactory;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TracingChannelDuplexHandler extends ChannelDuplexHandler {
  private final ContextSnapshotFactory contextSnapshotFactory;

  public TracingChannelDuplexHandler(ContextSnapshotFactory contextSnapshotFactory) {
    this.contextSnapshotFactory = contextSnapshotFactory;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try (final ContextSnapshot.Scope scope =
        contextSnapshotFactory.setThreadLocalsFrom(ctx.channel())) {
      ctx.fireChannelRead(msg);
    }
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    try (ContextSnapshot.Scope scope = contextSnapshotFactory.setThreadLocalsFrom(ctx.channel())) {
      ctx.write(msg, promise);
    }
  }

  @Override
  public void flush(ChannelHandlerContext ctx) throws Exception {
    try (ContextSnapshot.Scope snapshot =
        contextSnapshotFactory.setThreadLocalsFrom(ctx.channel())) {
      ctx.flush();
    }
  }
}
