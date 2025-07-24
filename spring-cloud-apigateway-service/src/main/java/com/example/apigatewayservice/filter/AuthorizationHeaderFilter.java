package com.example.apigatewayservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;
    
    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {
        // Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
    	log.info("### AuthorizationHeaderFilter.apply()--start[" + config + "]");
    	
        return (exchange, chain) -> {
        	log.info("### AuthorizationHeaderFilter.apply()--middle[" + config + "]");
            ServerHttpRequest request = exchange.getRequest();

            /*
             * 1. 로그인을 했을때 받았던 권한정보(토큰)을 서버로부터 반환 받는다.
             * 2. 이 토큰 정보가 유효한지 검증한후 결과를 반환한다.
             * 
             * 클라이언트는 로그인에서 토큰을 발급받고 이후 거래에서 부터는 이 토큰을 가지고 다니면서 권한을 인증받아야 된다.
             * 토큰은 헤더에 포함된다.
             * 헤더의 토큰정보는 Bearer라는 문자열을 키로 값을 가진다.
             */
            
            // 헤더에 토큰정보가 있는가 검사한다.
            if (!request.getHeaders().containsKey("Authorization")) {
            	log.info("### - AuthorizationHeaderFilter.apply()--middle-2[" + config + "]");
            	// 토큰이 없어서 에러를 반환한다. 권한이 없다.
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            // 헤더에 토큰정보가 존재한다.
            log.info("### - AuthorizationHeaderFilter.apply()--middle-3[" + config + "]");
            // 하나의 토큰을 가지고 온다.
            String authorizationHeader = request.getHeaders().get("Authorization").get(0);
            
            String jwt = authorizationHeader.replace("Bearer", "");

            // Create a cookie object
//            ServerHttpResponse response = exchange.getResponse();
//            ResponseCookie c1 = ResponseCookie.from("my_token", "test1234").maxAge(60 * 60 * 24).build();
//            response.addCookie(c1);

            // 토큰이 정상적인지 검증한다.
            if (!isJwtValid(jwt)) {
            	// 권한이 없다는 에러를 클라이언트로 반환한다.
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            // 정상적이면 exchange를 반환한다.
            log.info("###- AuthorizationHeaderFilter.apply()--end-3[" + exchange + "]");
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;

        try {
        	// 여기서 서브젝트는 우리가 토큰으로 만들때 사용한 userId가 된다.        	
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();
        } catch (Exception ex) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }
        
        /*
         * 여기서 subject는 userId이니 이것을 user서비스에서 조회해서 존재하는 지 체킹도 가능하다.
         */
        
        

        return returnValue;
    }

}