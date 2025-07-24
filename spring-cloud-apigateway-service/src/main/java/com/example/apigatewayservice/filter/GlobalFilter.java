package com.example.apigatewayservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

    	System.out.println("########################## - GlobalFilter.apply()--start[" + config + "]");

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());
            
            if (config.isPreLogger()) {
                log.info("Global Filter Start: request id -> {}", request.getId());
            }
            
            {
            	System.out.println("***************************************");          	
            	
            	for(int i = 0 ; i <100 ; i++) {
            		System.out.print(i);
            	}
            	log.info("\n***************************************");            	
            }


            // chain.filter(exchange).then(Mono.fromRunnable(() 거래 종료시 종료하지 말고 다음의 로직 수행해다오
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
            	
            	log.info("거래종료이후 호출 - GlobalFilter.apply()-끝");
            	
                if (config.isPostLogger()) {
                    log.info("#### Global Filter End: response code -> {}", response.getStatusCode());
                    System.out.println("########################## - GlobalFilter.apply()--middle[" + config + "]");
                }
                System.out.println("\n POST LOGGING ***************************************");
                System.out.println("########################## - GlobalFilter.apply()--end[" + "]");
            }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
