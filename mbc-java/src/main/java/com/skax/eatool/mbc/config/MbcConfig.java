package com.skax.eatool.mbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MBC Application Configuration
 * 
 * Spring Boot 기반 설정으로, XML 설정 파일 대신 Java 기반 설정을 사용합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 */
@Configuration
@EnableTransactionManagement
public class MbcConfig {

    // Spring Boot에서는 application.yml과 Java 기반 설정을 사용합니다.
    // XML 설정 파일은 더 이상 필요하지 않습니다.

}