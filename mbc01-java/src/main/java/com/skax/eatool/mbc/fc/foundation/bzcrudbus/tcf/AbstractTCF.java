package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tcf;

/**
 * TCF 추상 클래스
 * 
 * 프로그램명: AbstractTCF.java
 * 설명: This is oversea banking package.
 * 트랜잭션을 처리하면서 각 비즈니스 컴포넌트들을 호출한다.
 * 전체 구조는 3개의 부분으로 구성되어 있다.
 * 1. STF
 * 트랜잭션을 시작하기 위한 기본적인 설정을 담당한다.
 * 2. BTF
 * 비즈니스 로직을 처리하기 위해 호출된다.
 * 3. ETF
 * 트랜잭션을 완료를 처리하기 위해 호출되는 부분이다.
 * 
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - TCF 프레임워크의 추상 클래스
 * - 컨텍스트 정보 관리
 * - STF, BTF, ETF 구조의 기본 정의
 * 
 * @version 1.0
 */
public abstract class AbstractTCF {

	/** 컨텍스트 정보 */
	protected String ctx;

	/**
	 * 기본 생성자.
	 * 
	 * @see java.lang.Object#Object().
	 */
	public AbstractTCF() {
		ctx = null;
	}
}
