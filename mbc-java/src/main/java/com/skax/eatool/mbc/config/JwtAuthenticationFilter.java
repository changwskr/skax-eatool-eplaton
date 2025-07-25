package com.skax.eatool.mbc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 토큰 기반 인증 필터
 * 
 * 개발 환경에서 토큰 기반 인증을 구현합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        logger.debug("JWT Filter - Request: {} {}", method, requestURI);
        
        // EPlaton API는 토큰 검증 제외
        if (requestURI.startsWith("/mbc/eplaton/") || requestURI.startsWith("/eplaton/")) {
            logger.debug("EPlaton API - 토큰 검증 제외: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        
        // 정적 리소스는 토큰 검증 제외
        if (requestURI.startsWith("/static/") || 
            requestURI.startsWith("/css/") || 
            requestURI.startsWith("/js/") || 
            requestURI.startsWith("/images/") ||
            requestURI.startsWith("/h2-console/") ||
            requestURI.startsWith("/swagger-ui/") ||
            requestURI.startsWith("/v3/api-docs/") ||
            requestURI.startsWith("/actuator/")) {
            logger.debug("정적 리소스 - 토큰 검증 제외: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        
        // 기본 페이지는 토큰 검증 제외
        if (requestURI.equals("/") || 
            requestURI.equals("/home") || 
            requestURI.equals("/mbc") ||
            requestURI.equals("/mbc/")) {
            logger.debug("기본 페이지 - 토큰 검증 제외: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        
        // JWT 토큰 추출
        String jwtToken = extractJwtToken(request);
        
        if (jwtToken != null && validateJwtToken(jwtToken)) {
            // 토큰이 유효한 경우 인증 설정
            String username = extractUsernameFromToken(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("JWT 토큰 인증 성공: {}", username);
        } else {
            logger.debug("JWT 토큰 없음 또는 유효하지 않음: {}", requestURI);
        }
        
        filterChain.doFilter(request, response);
    }
    
    /**
     * HTTP 요청에서 JWT 토큰 추출
     */
    private String extractJwtToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    /**
     * JWT 토큰 유효성 검증 (개발 환경용 간단한 검증)
     */
    private boolean validateJwtToken(String token) {
        // 개발 환경에서는 간단한 검증
        // 실제 운영 환경에서는 JWT 라이브러리를 사용하여 검증
        return token != null && !token.isEmpty() && token.length() > 10;
    }
    
    /**
     * JWT 토큰에서 사용자명 추출 (개발 환경용)
     */
    private String extractUsernameFromToken(String token) {
        // 개발 환경에서는 간단한 추출
        // 실제 운영 환경에서는 JWT 라이브러리를 사용하여 추출
        return "dev-user";
    }
} 