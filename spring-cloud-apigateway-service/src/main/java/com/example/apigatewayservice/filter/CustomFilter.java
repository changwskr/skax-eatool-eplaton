package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
        return (exchange, chain) -> {
        	
        	ServerHttpRequest request = exchange.getRequest();
        	ServerHttpResponse response = exchange.getResponse();

        	log.info("★★★★★★★★★★★★★Custom PRE filter: request id -> {}", request.getId());
            {
            	System.out.println("##########################");          	
            	
            	for(int i = 0 ; i <100 ; i++) {
            		System.out.print(i);
            	}
            	System.out.println("\n##########################");            	
            }

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            	if(response.getStatusCode() != HttpStatus.OK ) {
            		
            		log.warn("호출 에러 발생-{"+request.getBody()+"}" + request.getHeaders());
            	}
            	log.warn("★★★★★★★호출 발생-{"+request.getBody()+"}" + request.getHeaders());
                log.info("Custom POST filter: response code -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}
