package com.skax.eatool.mbc.fc.foundation.bzcrudbus.log;

// import com.kbstar.ksa.logger.KesaLoggerFactory;

/**
 * IFRS 로거 팩토리 클래스
 * 
 * 프로그램명: IfrsLoggerFactory.java
 * 설명: IFRS 로거를 생성하는 팩토리 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IFRS 로거 생성
 * - 로거 인스턴스 관리
 * - 로거 설정 관리
 * 
 * @version 1.0
 */
public class IfrsLoggerFactory {

	/**
	 * 기본 생성자
	 */
	private IfrsLoggerFactory() {
	}

	/**
	 * IFRS 로거를 반환한다.
	 * 
	 * @param name 로거 이름
	 * @return IFRS 로거
	 */
	public static IIfrsLogger getLogger(String name) {
		// return KesaLoggerFactory.getLogger(name);
		return null; // 임시로 null 반환
	}

	/**
	 * IFRS 로거를 반환한다.
	 * 
	 * @param clazz 클래스
	 * @return IFRS 로거
	 */
	public static IIfrsLogger getLogger(Class<?> clazz) {
		// return KesaLoggerFactory.getLogger(clazz);
		return null; // 임시로 null 반환
	}

	/*
	 * 나머지 메서드들은 주석 처리
	 */
}
