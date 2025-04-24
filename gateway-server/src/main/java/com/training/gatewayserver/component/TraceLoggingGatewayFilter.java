//package com.training.gatewayserver.component;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//@Component
//public class TraceLoggingGatewayFilter implements GlobalFilter {
//
//    private static final Logger log = LoggerFactory.getLogger(TraceLoggingGatewayFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String traceId = exchange.getRequest().getHeaders().getFirst("X-B3-TraceId");
//        log.info("Gateway Request Trace ID: {}", traceId);
//        return chain.filter(exchange);
//    }
//}
