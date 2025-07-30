package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Global Gateway Filter.
 *
 * Spring Cloud Gateway의 글로벌 필터입니다.
 * 모든 요청에 대해 공통적으로 적용되는 필터입니다.
 *
 * @author SKAX
 * @version 1.0.0
 * @since 2024
 */
@Component
@Slf4j
public class GlobalGatewayFilter implements GlobalFilter, Ordered {

    /**
     * 필터 실행 순서.
     */
    private static final int ORDER = 1;

    /**
     * 필터 실행 순서를 반환합니다.
     *
     * @return 필터 순서
     */
    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, 
                            final GatewayFilterChain chain) {
        log.info("★★★★★★★★★★★★★Global filter: request id -> {}", 
                exchange.getRequest().getId());

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        log.info("★★★★★★★★★★★★★Global filter: request uri -> {}", 
                request.getURI());

        // 요청 헤더 로깅
        request.getHeaders().forEach((key, value) -> {
            log.info("★★★★★★★★★★★★★Global filter: request header -> {}: {}", 
                    key, value);
        });

        // 응답 처리
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("★★★★★★★★★★★★★Global filter: response status -> {}", 
                    response.getStatusCode());
        }));
    }
}
