package com.training.gatewayserver.component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.URI;
import java.util.List;

@Component
public class CpuBasedLoadBalancerFilter implements GatewayFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CpuBasedLoadBalancerFilter.class);
    private final DiscoveryClient discoveryClient;

    public CpuBasedLoadBalancerFilter(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<ServiceInstance> instances = discoveryClient.getInstances("CUSTOMER-SERVICE");

        if (instances == null || instances.isEmpty()) {
            log.warn("No instances found");
            log.warn("No instances found for CUSTOMER-SERVICE");
            return chain.filter(exchange);
        }

        double systemLoad = getSystemCpuLoad();
        log.info("System CPU Load: {}%", systemLoad * 100);

        // Choose instance based on load
        ServiceInstance chosen = systemLoad < 0.7 ? instances.get(0) : instances.get(instances.size() - 1);
        log.info("Routing to instance: {}:{} (CPU Load: {}%)",
                chosen.getHost(), chosen.getPort(), systemLoad * 100);

        //URI uri = chosen.getUri();
        URI uri = URI.create(chosen.getUri().toString());
        URI newUri = UriComponentsBuilder.fromUri(uri)
                .path(exchange.getRequest().getURI().getPath())
                .query(exchange.getRequest().getURI().getQuery())
                .build(true)
                .toUri();


        ServerHttpRequest request = exchange.getRequest().mutate()
                .uri(URI.create(newUri.toString() + exchange.getRequest().getURI().getPath()))
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
            log.error("Failed to fetch system CPU load", e);
            return 0.0;
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
