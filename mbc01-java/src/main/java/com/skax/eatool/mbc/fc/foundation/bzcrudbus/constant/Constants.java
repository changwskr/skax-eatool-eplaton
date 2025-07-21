package com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant;

/**
 * 시스템 상수 정의 클래스
 * 
 * 프로그램명: Constants.java
 * 설명: IFRS 프레임워크에서 사용되는 시스템 상수들을 정의하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 트랜잭션 모드 상수 정의
 * - XML 노드 이름 상수 정의
 * - 서비스 결과 관련 상수 정의
 * - 에러 처리 관련 상수 정의
 * - 데이터 타입 상수 정의
 */
public class Constants {

	/** WAF 트랜잭션 모드 */
	public static final String TRANSACTION_MODE_WAF = "WAF";
	/** 서버 트랜잭션 모드 */
	public static final String TRANSACTION_MODE_SERVER = "SERVER";

	/** 입력 데이터 경로 */
	public static final String INDATA = "/KB-Message/Individual/InData";
	/** 출력 데이터 경로 */
	public static final String OUTDATA = "/KB-Message/Individual";

	/** KESA XML 개별 노드 이름1 */
	public static final String KESA_XML_INNODE_NAME1 = "Individual";
	/** KESA XML 데이터 헤더 노드 이름 */
	public static final String KESA_XML_INNODE_NAME2 = "DataHeader";
	/** KESA XML 입력 데이터 노드 이름 */
	public static final String KESA_XML_INNODE_NAME3 = "InData";
	/** KESA XML 파라미터 노드 이름 */
	public static final String KESA_XML_INNODE_NAME4 = "Params";

	/** 서비스 결과 변수명 */
	public static final String SERVICE_RESULT_VAR = "hashResult";
	/** 에러 발생 변수명 */
	public static final String ERROR_OCCUR_VAR = "error.occur";
	/** 에러 객체 변수명 */
	public static final String ERROR_OBJECT = "error.exception";
	/** 에러 메시지 텍스트 변수명 */
	public static final String ERROR_MSG_TEXT_VAR = "error.msg";
	/** 에러 메시지 코드 변수명 */
	public static final String ERROR_MSG_CD_VAR = "error.msgcd";
	/** 맵 포워드 변수명 */
	public static final String MAP_FORWARD_VAR = "map.forward";
	/** 결과 XML 변수명 */
	public static final String RESULT_XML_VAR = "result_xml";
	/** 단말 타입 - TrustForm */
	public static final String DANMAL_TYPE_TRUSTFORM = "T";
	/** 단말 타입 - IWorks */
	public static final String DANMAL_TYPE_IWORKS = "I";

}
