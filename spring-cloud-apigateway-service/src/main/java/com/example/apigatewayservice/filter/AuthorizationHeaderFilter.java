package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Authorization Header Filter.
 *
 * JWT 토큰을 검증하는 Spring Cloud Gateway 필터입니다.
 * 요청 헤더의 Authorization 토큰을 검증하여 인증된 요청만 통과시킵니다.
 *
 * @author SKAX
 * @version 1.0.0
 * @since 2024
 */
@Component
@Slf4j
public class AuthorizationHeaderFilter 
    extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    /** Environment 인스턴스 */
    private final Environment env;

    /**
     * 생성자.
     *
     * @param env Environment 인스턴스
     */
    public AuthorizationHeaderFilter(final Environment env) {
        super(Config.class);
        this.env = env;
    }

    /**
     * 필터 설정 클래스.
     */
    public static class Config {
        // 설정 속성들을 여기에 추가
    }

    @Override
    public GatewayFilter apply(final Config config) {
        log.info("### AuthorizationHeaderFilter.apply()--start[{}]", config);

        return (exchange, chain) -> {
            log.info("### AuthorizationHeaderFilter.apply()--middle[{}]", config);
            ServerHttpRequest request = exchange.getRequest();

            /*
             * 1. 로그인을 했을때 받았던 권한정보(토큰)을 서버로부터 반환 받는다.
             * 2. 이 토큰 정보가 유효한지 검증한후 결과를 반환한다.
             *
             * 클라이언트는 로그인에서 토큰을 발급받고 이후 거래에서 부터는 이 토큰을 가지고 
             * 다니면서 권한을 인증받아야 된다. 토큰은 헤더에 포함된다.
             * 헤더의 토큰정보는 Bearer라는 문자열을 키로 값을 가진다.
             */

            // 헤더에 토큰정보가 있는가 검사한다.
            if (!request.getHeaders().containsKey("Authorization")) {
                log.info("### - AuthorizationHeaderFilter.apply()--middle-2[{}]", config);
                // 토큰이 없어서 에러를 반환한다. 권한이 없다.
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            // 헤더에 토큰정보가 존재한다.
            log.info("### - AuthorizationHeaderFilter.apply()--middle-3[{}]", config);
            // 하나의 토큰을 가지고 온다.
            String authorizationHeader = request.getHeaders().get("Authorization").get(0);

            String jwt = authorizationHeader.replace("Bearer", "");

            // Create a cookie object
            // ServerHttpResponse response = exchange.getResponse();
            // ResponseCookie c1 = ResponseCookie.from("my_token", "test1234")
            //     .maxAge(60 * 60 * 24).build();
            // response.addCookie(c1);

            // 토큰이 정상적인지 검증한다.
            if (!isJwtValid(jwt)) {
                // 권한이 없다는 에러를 클라이언트로 반환한다.
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            // 정상적이면 exchange를 반환한다.
            log.info("###- AuthorizationHeaderFilter.apply()--end-3[{}]", exchange);
            return chain.filter(exchange);
        };
    }

    /**
     * 에러 처리 메서드.
     *
     * @param exchange ServerWebExchange 인스턴스
     * @param err 에러 메시지
     * @param httpStatus HTTP 상태 코드
     * @return Mono<Void>
     */
    private Mono<Void> onError(final ServerWebExchange exchange, final String err, 
                               final HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    /**
     * JWT 토큰 유효성 검증.
     *
     * @param jwt JWT 토큰 문자열
     * @return 유효성 여부
     */
    private boolean isJwtValid(final String jwt) {
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

        return returnValue;
    }
}