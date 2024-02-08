// package com.gateway.configuration;
//
// import io.netty.channel.ChannelDuplexHandler;
// import io.netty.channel.ChannelHandlerContext;
// import io.netty.channel.ChannelPromise;
// import io.netty.handler.codec.http.HttpRequest;
// import io.netty.handler.codec.http.LastHttpContent;
//
// public final class LogbookServerHandler extends ChannelDuplexHandler {
//    public LogbookServerHandler() {
//    }
//
//    @Override
//    public void channelRead(
//            final ChannelHandlerContext context,
//            final Object message) {
//
//        Conditionals.runIf(message, HttpRequest.class, httpRequest -> {
////            System.out.println("server read begin");
//        });
//
//        Conditionals.runIf(message, LastHttpContent.class, content -> {
//            System.out.println("server incoming");
//        });
//
//        context.fireChannelRead(message);
//    }
//
//    @Override
//    public void write(
//            final ChannelHandlerContext context,
//            final Object message,
//            final ChannelPromise promise) {
//
//        Conditionals.runIf(message, HttpRequest.class, httpRequest -> {
////            System.out.println("server write begin");
//        });
//
//        Conditionals.runIf(message, LastHttpContent.class, content -> {
//            System.out.println("server outgoing");
//        });
//
//        context.write(message, promise);
//    }
//
// }
