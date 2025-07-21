package com.skax.eatool.mbc.fc.foundation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 데이터 접근 방식 팩토리 클래스
 * 
 * 프로그램명: DataAccessFactory.java
 * 설명: MyBatis와 JPA 중 선택할 수 있는 팩토리 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 데이터 접근 방식 확인
 * - 프로파일 기반 설정
 * - 동적 데이터 접근 방식 선택
 * 
 * @version 1.0
 */
@Configuration
public class DataAccessFactory {

    @Value("${spring.profiles.active:mybatis}")
    private String activeProfile;

    @Value("${app.data-access.type:mybatis}")
    private String dataAccessType;

    /**
     * 현재 활성화된 데이터 접근 방식 확인
     * 
     * @return 데이터 접근 방식 (mybatis/jpa)
     */
    public String getCurrentDataAccessType() {
        // 프로파일이 jpa인 경우 JPA 사용
        if ("jpa".equals(activeProfile)) {
            return "jpa";
        }
        // 프로파일이 mybatis이거나 default인 경우 MyBatis 사용
        return "mybatis";
    }

    /**
     * JPA 사용 여부 확인
     * 
     * @return JPA 사용 여부
     */
    public boolean isJpaEnabled() {
        return "jpa".equals(getCurrentDataAccessType());
    }

    /**
     * MyBatis 사용 여부 확인
     * 
     * @return MyBatis 사용 여부
     */
    public boolean isMyBatisEnabled() {
        return "mybatis".equals(getCurrentDataAccessType());
    }

    /**
     * 현재 활성 프로파일 확인
     * 
     * @return 활성 프로파일
     */
    public String getActiveProfile() {
        return activeProfile;
    }

    /**
     * 설정된 데이터 접근 타입 확인
     * 
     * @return 설정된 데이터 접근 타입
     */
    public String getDataAccessType() {
        return dataAccessType;
    }

    /**
     * 데이터 접근 방식 정보 출력
     * 
     * @return 데이터 접근 방식 정보
     */
    public String getDataAccessInfo() {
        return String.format("Active Profile: %s, Data Access Type: %s, Current: %s",
                activeProfile, dataAccessType, getCurrentDataAccessType());
    }
}