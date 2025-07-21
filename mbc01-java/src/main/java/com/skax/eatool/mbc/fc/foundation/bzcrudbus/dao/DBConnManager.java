package com.skax.eatool.mbc.fc.foundation.bzcrudbus.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.MyBatisConfig;

/**
 * 데이터베이스 연결 관리자 클래스
 * 
 * 프로그램명: DBConnManager.java
 * 설명: MyBatis SqlSession을 관리하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - SqlSessionFactory 초기화
 * - SqlSession 관리
 * - 데이터베이스 연결 관리
 * 
 * @version 1.0
 */
public class DBConnManager {

	/** SqlSessionFactory 인스턴스 */
	private static SqlSessionFactory sqlSessionFactory = null;

	/** DataSource 인스턴스 */
	private static DataSource dataSource = null;

	/**
	 * 기본 생성자
	 */
	private DBConnManager() {
	}

	/**
	 * SqlSessionFactory를 초기화한다.
	 * 
	 * @throws Exception 초기화 실패시
	 */
	public static void initialize() throws Exception {
		if (sqlSessionFactory == null) {
			sqlSessionFactory = MyBatisConfig.getSqlSessionFactory();
		}
	}

	/**
	 * DataSource를 설정하고 SqlSessionFactory를 초기화한다.
	 * 
	 * @param ds 데이터 소스
	 * @throws Exception 초기화 실패시
	 */
	public static void initialize(DataSource ds) throws Exception {
		dataSource = ds;
		sqlSessionFactory = MyBatisConfig.createSqlSessionFactory(dataSource);
	}

	/**
	 * SqlSessionFactory를 반환한다.
	 * 
	 * @return SqlSessionFactory 인스턴스
	 * @throws Exception 초기화되지 않은 경우
	 */
	public static SqlSessionFactory getSqlSessionFactory() throws Exception {
		if (sqlSessionFactory == null) {
			initialize();
		}
		return sqlSessionFactory;
	}

	/**
	 * SqlSession을 반환한다.
	 * 
	 * @return SqlSession 인스턴스
	 * @throws Exception 초기화되지 않은 경우
	 */
	public static SqlSession getSqlSession() throws Exception {
		return getSqlSessionFactory().openSession();
	}

	/**
	 * 자동 커밋 SqlSession을 반환한다.
	 * 
	 * @return SqlSession 인스턴스
	 * @throws Exception 초기화되지 않은 경우
	 */
	public static SqlSession getSqlSession(boolean autoCommit) throws Exception {
		return getSqlSessionFactory().openSession(autoCommit);
	}

	/**
	 * SqlSession을 닫는다.
	 * 
	 * @param session 닫을 SqlSession
	 */
	public static void closeSqlSession(SqlSession session) {
		if (session != null) {
			session.close();
		}
	}

	/**
	 * DataSource를 반환한다.
	 * 
	 * @return DataSource 인스턴스
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
}
