package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis 설정 클래스
 * 
 * 프로그램명: MyBatisConfig.java
 * 설명: MyBatis SqlSessionFactory를 설정하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - SqlSessionFactory 초기화
 * - MyBatis 설정 파일 로드
 * - 데이터베이스 연결 설정
 * 
 * @version 1.0
 */
public class MyBatisConfig {

    private static SqlSessionFactory sqlSessionFactory;

    /**
     * MyBatis 설정 파일 경로
     */
    private static final String MYBATIS_CONFIG_PATH = "mybatis-config.xml";

    /**
     * SqlSessionFactory를 반환한다.
     * 
     * @return SqlSessionFactory 인스턴스
     * @throws IOException 설정 파일 로드 실패시
     */
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        if (sqlSessionFactory == null) {
            synchronized (MyBatisConfig.class) {
                if (sqlSessionFactory == null) {
                    sqlSessionFactory = createSqlSessionFactory();
                }
            }
        }
        return sqlSessionFactory;
    }

    /**
     * SqlSessionFactory를 생성한다.
     * 
     * @return SqlSessionFactory 인스턴스
     * @throws IOException 설정 파일 로드 실패시
     */
    private static SqlSessionFactory createSqlSessionFactory() throws IOException {
        try (InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG_PATH)) {
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            // 설정 파일이 없으면 기본 설정으로 생성
            return createDefaultSqlSessionFactory();
        }
    }

    /**
     * 기본 설정으로 SqlSessionFactory를 생성한다.
     * 
     * @return SqlSessionFactory 인스턴스
     */
    private static SqlSessionFactory createDefaultSqlSessionFactory() {
        Configuration configuration = new Configuration();

        // 기본 설정
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType(org.apache.ibatis.session.ExecutorType.SIMPLE);

        // 로깅 설정
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

        // 환경 설정 (DataSource는 나중에 주입)
        Environment environment = new Environment("default", new JdbcTransactionFactory(), null);
        configuration.setEnvironment(environment);

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    /**
     * DataSource를 사용하여 SqlSessionFactory를 생성한다.
     * 
     * @param dataSource 데이터 소스
     * @return SqlSessionFactory 인스턴스
     */
    public static SqlSessionFactory createSqlSessionFactory(DataSource dataSource) {
        Configuration configuration = new Configuration();

        // 기본 설정
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType(org.apache.ibatis.session.ExecutorType.SIMPLE);

        // 로깅 설정
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

        // 환경 설정
        Environment environment = new Environment("default", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);

        return new SqlSessionFactoryBuilder().build(configuration);
    }
}