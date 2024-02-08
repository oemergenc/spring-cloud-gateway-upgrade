// package com.gateway.configuration;
//
// import io.micrometer.context.ContextSnapshotFactory;
// import io.netty.channel.ChannelDuplexHandler;
// import io.netty.channel.ChannelHandlerContext;
// import io.netty.channel.ChannelPromise;
// import io.netty.handler.codec.http.HttpRequest;
// import io.netty.handler.codec.http.HttpResponse;
// import io.netty.handler.codec.http.LastHttpContent;
// import io.netty.util.AttributeKey;
//
// import static com.gateway.configuration.Conditionals.runIf;
//
// public final class LogbookClientHandler extends ChannelDuplexHandler {
//    public final static String BLA="bla";
//    public static final AttributeKey<String> ATTRIBUTE_KEY = AttributeKey.valueOf(BLA);
//
//    public LogbookClientHandler() {}
//
//    @Override
//    public void write(
//            final ChannelHandlerContext context,
//            final Object message,
//            final ChannelPromise promise) {
//
//        runIf(message, HttpRequest.class, httpRequest -> {
//        });
//
//
//        runIf(message, LastHttpContent.class, content -> {
//            System.out.println("client outgoing");
//        });
//
//        context.write(message, promise);
//    }
//
//    @Override
//    public void channelRead(
//            final ChannelHandlerContext context,
//            final Object message) {
//
//        runIf(message, HttpResponse.class, httpResponse -> {
////            System.out.println("client read ");
//        });
//
//        runIf(message, LastHttpContent.class, content -> {
//            System.out.println("client incoming");
//        });
//
//        context.fireChannelRead(message);
//    }
//
// }
