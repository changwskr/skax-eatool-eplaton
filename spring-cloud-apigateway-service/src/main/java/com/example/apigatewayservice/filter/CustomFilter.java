package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Custom Filter.
 *
 * Spring Cloud Gateway의 커스텀 필터입니다.
 * 요청과 응답을 로깅하고 처리합니다.
 *
 * @author SKAX
 * @version 1.0.0
 * @since 2024
 */
@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    /**
     * 생성자.
     */
    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(final Config config) {
        // Custom Pre Filter
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("★★★★★★★★★★★★★Custom PRE filter: request id -> {}", request.getId());
            
            System.out.println("##########################");

            for (int i = 0; i < 100; i++) {
                System.out.print(i);
            }
            System.out.println("\n##########################");

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (response.getStatusCode() != HttpStatus.OK) {
                    log.warn("호출 에러 발생-{} {}", request.getBody(), request.getHeaders());
                }
                log.warn("★★★★★★★호출 발생-{} {}", request.getBody(), request.getHeaders());
                log.info("Custom POST filter: response code -> {}", response.getStatusCode());
            }));
        };
    }

    /**
     * 필터 설정 클래스.
     */
    public static class Config {
        // 설정 속성들을 여기에 추가
    }
}
