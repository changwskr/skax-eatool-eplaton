package com.skax.eatool.mbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 데이터 접근 방식 설정 클래스
 * 
 * 프로그램명: DataAccessConfig.java
 * 설명: MyBatis와 JPA 중 선택할 수 있는 설정 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - MyBatis 프로파일 설정
 * - JPA 프로파일 설정
 * - 데이터 접근 방식 선택
 * 
 * 사용 방법:
 * - MyBatis 사용: -Dspring.profiles.active=mybatis
 * - JPA 사용: -Dspring.profiles.active=jpa
 * - 기본값: MyBatis
 * 
 * @version 1.0
 */
@Configuration
public class DataAccessConfig {

    /**
     * MyBatis 설정 (기본값)
     * 
     * @return MyBatisConfiguration
     */
    @Configuration
    @Profile("mybatis")
    public static class MyBatisConfiguration {
        // MyBatis 관련 설정은 MyBatisConfig에서 처리
    }

    /**
     * JPA 설정
     * 
     * @return JpaConfiguration
     */
    @Configuration
    @Profile("jpa")
    public static class JpaConfiguration {
        // JPA 관련 설정은 application.yml에서 처리
    }

    /**
     * 기본 설정 (MyBatis)
     * 
     * @return DefaultConfiguration
     */
    @Configuration
    @Profile("default")
    public static class DefaultConfiguration {
        // 기본적으로 MyBatis 사용
    }
}