package com.skax.eatool.mbc.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * MyBatis 설정 클래스
 * 
 * 프로그램명: MyBatisConfig.java
 * 설명: MyBatis 관련 설정을 담당하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - SqlSessionFactory 설정
 * - Mapper 스캔 설정
 * - 트랜잭션 매니저 설정
 * 
 * @version 1.0
 */
@Configuration
@Profile({ "mybatis", "default" })
@MapperScan(basePackages = "com.kbstar.mbc.fc.foundation.mapper")
public class MyBatisConfig {

    /**
     * SqlSessionFactory 설정
     * 
     * @param dataSource 데이터소스
     * @return SqlSessionFactory
     * @throws Exception 설정 오류
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // Mapper XML 파일 위치 설정
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/*.xml"));

        // MyBatis 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    /**
     * 트랜잭션 매니저 설정
     * 
     * @param dataSource 데이터소스
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}