package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

    	// 람다식 표현으로 구현 -1
//    	return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Global Filter baseMessage: {}", config.getBaseMessage());
//            if (config.isPreLogger()) {
//                log.info("Global Filter Start: request id -> {}", request.getId());
//            }
//            
//            // 서비스가 종료 되었나요 그럼 실행합니다.
//            return chain.filter(exchange).then(Mono.fromRunnable(()->{
//                if (config.isPostLogger()) {
//                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
//                }
//            }));
//        });
   	
//---------------------------------- 2022-11-26-3 위의 람다식을 이형식으로 바꾼 것임
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("★★★ Logging Filter baseMessage: {}", config.getBaseMessage());
            
            if (config.isPreLogger()) {
                log.info("★★★ Logging PRE Filter: request id -> {}", request.getId());
            }
            
            // 서비스를 종료이후 타는 후처리
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    log.info("★★★ Logging POST Filter: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.HIGHEST_PRECEDENCE);  // 여기서 필터의 우선순위를 변경한다. 현재 가장 높은 순위여서 글로벌필터보다 먼저 수행된다.
        
        // Ordered.LOWEST_PRECEDENCE // 우선순위가 가장 낮다 
        return filter;
        
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
