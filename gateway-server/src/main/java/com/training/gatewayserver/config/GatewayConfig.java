package com.training.gatewayserver.config;

import com.training.gatewayserver.component.CpuBasedLoadBalancerFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final CpuBasedLoadBalancerFilter cpuFilter;

    public GatewayConfig(CpuBasedLoadBalancerFilter cpuFilter) {
        this.cpuFilter = cpuFilter;
    }
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customerID", r -> r.path("/api/v1/customer/**")
                        //.filters(f -> f.filter(cpuFilter))
                        .uri("lb://customer-service"))
                .route("transactionID", r->r.path("/api/v1/transaction/**").uri("lb://transaction-service"))
                .build();
    }
}
