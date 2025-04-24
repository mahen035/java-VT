package com.training.gatewayserver.component;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.URI;
import java.util.List;

@Component
public class CpuBasedLoadBalancerFilter implements GatewayFilter, Ordered {

    private final DiscoveryClient discoveryClient;

    public CpuBasedLoadBalancerFilter(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<ServiceInstance> instances = discoveryClient.getInstances("CUSTOMER-SERVICE");

        if (instances == null || instances.isEmpty()) {
            return chain.filter(exchange);
        }
        double systemLoad = getSystemCpuLoad();
        ServiceInstance chosen = systemLoad < 0.7 ? instances.getFirst() : instances.getLast();
        URI uri = chosen.getUri();
        ServerHttpRequest request = exchange.getRequest().mutate()
                .uri(URI.create(uri.toString() + exchange.getRequest().getURI().getPath()))
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    private double getSystemCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        try {
            var method = osBean.getClass().getMethod("getSystemCpuLoad");
            method.setAccessible(true);
            return (double) method.invoke(osBean);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
