// package com.gateway.configuration;
//
// import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
// import
// org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper;
// import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import reactor.netty.http.client.HttpClient;
// import reactor.netty.http.server.HttpServer;
//
// import static
// org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;
//
// @Configuration(proxyBeanMethods = false)
// public class LogbookWebFluxAutoConfiguration {
//
//    @Configuration(proxyBeanMethods = false)
//    @ConditionalOnClass(HttpServer.class)
//    @ConditionalOnWebApplication(type = REACTIVE)
//    static class WebFluxNettyServerConfiguration {
//
////        @Bean
////        public NettyServerCustomizer logbookNettyServerCustomizer() {
////            return httpServer -> httpServer.doOnConnection(connection ->
// connection.addHandlerLast(new LogbookServerHandler()));
////        }
//    }
//
//    @Configuration(proxyBeanMethods = false)
//    @ConditionalOnClass(HttpClient.class)
//    static class WebFluxNettyClientConfiguration {
//
////        @Bean
////        public ReactorNettyHttpClientMapper logbookNettyClientCustomizer() {
////            return httpClient -> httpClient.doOnConnected(connection ->
// connection.addHandlerLast(new LogbookClientHandler()));
////        }
//    }
// }
