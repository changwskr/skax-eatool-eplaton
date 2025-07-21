package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

/**
 * 명령어 객체 클래스
 * 
 * 프로그램명: CommandObj.java
 * 설명: 액션 설정에서 사용되는 명령어 정보를 담는 데이터 객체
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 명령어 타입, 키, 이름, 서버, 엘리먼트, 메서드, 순서, SQL ID 정보 저장
 * - 명령어 객체의 문자열 표현 제공
 * 
 * @author 개발자
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CommandObj {

	/** 명령어 타입 */
	public String type = "";
	/** 명령어 키 */
	public String key = "";
	/** 명령어 이름 */
	public String name = "";
	/** 서버 정보 */
	public String server = "";
	/** 엘리먼트 정보 */
	public String element = "";
	/** 메서드 정보 */
	public String method = "";
	/** 순서 정보 */
	public String seq = "";
	/** SQL ID */
	public String sqlId = "";

	/**
	 * 기본 생성자
	 * 
	 * @param type   명령어 타입
	 * @param key    명령어 키
	 * @param name   명령어 이름
	 * @param server 서버 정보
	 * @param group  엘리먼트 정보
	 * @param method 메서드 정보
	 * @param seq    순서 정보
	 * @param sqlId  SQL ID
	 */
	public CommandObj(String type, String key, String name, String server, String group, String method, String seq,
			String sqlId) {

		this.type = type;
		this.key = key;
		this.name = name;
		this.server = server;
		this.element = group;
		this.method = method;
		this.seq = seq;
		this.sqlId = sqlId;
	}

	/**
	 * 명령어 객체의 문자열 표현을 반환한다.
	 * 
	 * @return 명령어 객체 정보 문자열
	 */
	public String toString() {
		return "[type:" + type + "][key:" + key + "][server:" + server + "][element:" + element + "][name:" + name
				+ "][method:" + method + "][seq:" + seq + "][sql_id:" + sqlId + "]";
	}
}
