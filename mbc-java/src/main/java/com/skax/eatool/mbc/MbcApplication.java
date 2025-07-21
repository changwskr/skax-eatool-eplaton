package com.skax.eatool.mbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MBC Application
 * 
 * MBC 메인 스프링 부트 애플리케이션 클래스입니다.
 * mbc01-lib의 JAR 파일들을 의존성으로 사용합니다.
 * 
 * @author SKAX EA Tool Team
 * @version 1.0.0
 * @since 2024-01-01
 */
@SpringBootApplication
public class MbcApplication {

    /**
     * 애플리케이션 메인 메서드
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        SpringApplication.run(MbcApplication.class, args);
    }
} 