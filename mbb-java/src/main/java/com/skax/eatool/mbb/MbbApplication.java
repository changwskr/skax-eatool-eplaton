/**
 * (@)# MbbApplication.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : MBB AI Coding Workflow 메인 애플리케이션
 * 
 * 주요 기능:
 * - AI 코딩 워크플로우 관리
 * - 코드 생성 및 템플릿 처리
 * - 파일 시스템 관리
 * 
 * 변경이력 :
 *   <ul>
 *     <li>2024-01-01 :: SKAX Team :: 신규작성</li>
 *   </ul>
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
package com.skax.eatool.mbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * MBB AI Coding Workflow 메인 애플리케이션 클래스
 * 
 * AI 코딩 워크플로우를 관리하는 Spring Boot 애플리케이션입니다.
 * 코드 생성, 템플릿 처리, 파일 시스템 관리 기능을 제공합니다.
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication(scanBasePackages = "com.skax.eatool.mbb")
@EntityScan("com.skax.eatool.mbb.dc.entity")
@EnableJpaRepositories("com.skax.eatool.mbb.dc.repository")
@EnableAsync
@EnableScheduling
public class MbbApplication {

    /**
     * 애플리케이션 메인 메서드
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        SpringApplication.run(MbbApplication.class, args);
    }
} 