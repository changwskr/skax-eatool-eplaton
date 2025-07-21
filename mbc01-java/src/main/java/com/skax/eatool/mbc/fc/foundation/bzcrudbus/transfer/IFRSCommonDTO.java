package com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer;

import java.util.HashMap;
import java.util.Map;
// import com.skax.eatool.ksa.infra.po.AbstractDTO;
// import com.skax.eatool.ksa.infra.po.KBData;

/**
 * IFRS 공통 DTO 클래스
 * 
 * 프로그램명: IFRSCommonDTO.java
 * 설명: 이 클래스는 모든 request에서 공통적으로 사용되는 정보를 표현한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IFRS 시스템 공통 데이터 관리
 * - 국가, 시간대, 통화 정보 관리
 * - 명령 ID, 라우팅 AS 관리
 * - PC 클래스명, 메서드명 관리
 * - SQL ID, 키 관리
 * - 에러 정보 관리
 * - 시간 정보 관리 (입력/출력 시간)
 * - 사용자 정보 관리
 * 
 * @version 1.30
 */
public class IFRSCommonDTO /* extends AbstractDTO */ implements ICommonDTO {

	/** KBData 객체 */
	// private KBData kbData = null;

	/** 파라미터 맵 */
	private Map<String, Object> fromMap = new HashMap<String, Object>();

	/** 에러 코드 */
	private String errorCode = null;

	/** 에러 메시지 */
	private String errorMessage = null;

	/** 트랜잭션 번호 */
	private String transactionNo = null;

	/** 사용자 ID */
	private String userId = null;

	/** SQL ID */
	private String sqlId = null;

	/** 비즈니스 코드 */
	private String bizCode = null;

	/** 시퀀스 번호 */
	private String seqNo = null;

	/*
	 * 나머지 필드들과 메서드들은 주석 처리
	 */

	/**
	 * 기본 생성자
	 */
	public IFRSCommonDTO() {
	}

	/**
	 * 파라미터 맵을 반환한다.
	 * 
	 * @return 파라미터 맵
	 */
	public Map<String, Object> getFromMap() {
		return fromMap;
	}

	/**
	 * 파라미터 맵을 설정한다.
	 * 
	 * @param fromMap 파라미터 맵
	 */
	public void setFromMap(Map<String, Object> fromMap) {
		this.fromMap = fromMap;
	}

	/**
	 * 에러 코드를 반환한다.
	 * 
	 * @return 에러 코드
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 에러 코드를 설정한다.
	 * 
	 * @param errorCode 에러 코드
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 에러 메시지를 반환한다.
	 * 
	 * @return 에러 메시지
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 에러 메시지를 설정한다.
	 * 
	 * @param errorMessage 에러 메시지
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 트랜잭션 번호를 반환한다.
	 * 
	 * @return 트랜잭션 번호
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * 트랜잭션 번호를 설정한다.
	 * 
	 * @param transactionNo 트랜잭션 번호
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	/**
	 * 사용자 ID를 반환한다.
	 * 
	 * @return 사용자 ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 사용자 ID를 설정한다.
	 * 
	 * @param userId 사용자 ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * SQL ID를 반환한다.
	 * 
	 * @return SQL ID
	 */
	public String getSqlId() {
		return sqlId;
	}

	/**
	 * SQL ID를 설정한다.
	 * 
	 * @param sqlId SQL ID
	 */
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	/**
	 * 비즈니스 코드를 반환한다.
	 * 
	 * @return 비즈니스 코드
	 */
	public String getBizCode() {
		return bizCode;
	}

	/**
	 * 비즈니스 코드를 설정한다.
	 * 
	 * @param bizCode 비즈니스 코드
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	/**
	 * 시퀀스 번호를 반환한다.
	 * 
	 * @return 시퀀스 번호
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * 시퀀스 번호를 설정한다.
	 * 
	 * @param seqNo 시퀀스 번호
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 그리드 이름 배열을 반환한다.
	 * 
	 * @return 그리드 이름 배열
	 */
	public String[] getGridName() {
		// TODO: Implement when grid functionality is available
		return new String[0];
	}

	/**
	 * 그리드 이름 배열을 설정한다.
	 * 
	 * @param gridName 그리드 이름 배열
	 */
	public void setGridName(String[] gridName) {
		// TODO: Implement when grid functionality is available
	}

	/**
	 * WAF 시퀀스를 반환한다.
	 * 
	 * @return WAF 시퀀스
	 */
	public String getWafSeq() {
		// TODO: Implement when WAF functionality is available
		return "";
	}

	/**
	 * 명령 ID를 반환한다.
	 * 
	 * @return 명령 ID
	 */
	public String getCommandId() {
		// TODO: Implement when command functionality is available
		return "";
	}

	/**
	 * 키를 설정한다.
	 * 
	 * @param key 키
	 */
	public void setKey(String key) {
		// TODO: Implement when key functionality is available
	}

	/**
	 * 호스트 서버를 설정한다.
	 * 
	 * @param hostServer 호스트 서버
	 */
	public void setHostServer(String hostServer) {
		// TODO: Implement when host functionality is available
	}

	/**
	 * 호스트 AS를 설정한다.
	 * 
	 * @param hostAs 호스트 AS
	 */
	public void setHostAs(String hostAs) {
		// TODO: Implement when host functionality is available
	}

	/**
	 * 에러 맵을 설정한다.
	 * 
	 * @param errorMap 에러 맵
	 */
	public void setErrorMap(HashMap errorMap) {
		// TODO: Implement when error functionality is available
	}

	/**
	 * 결과 XML을 설정한다.
	 * 
	 * @param resultXml 결과 XML
	 */
	public void setResultXml(String resultXml) {
		// TODO: Implement when result functionality is available
	}

	/**
	 * 포워드 이름을 반환한다.
	 * 
	 * @return 포워드 이름
	 */
	public String getForwardName() {
		// TODO: Implement when forward functionality is available
		return "";
	}

	/**
	 * 출력 맵을 반환한다.
	 * 
	 * @return 출력 맵
	 */
	public HashMap getToMap() {
		// TODO: Implement when output functionality is available
		return new HashMap();
	}

	/**
	 * 에러 맵을 반환한다.
	 * 
	 * @return 에러 맵
	 */
	public HashMap getErrorMap() {
		// TODO: Implement when error functionality is available
		return new HashMap();
	}

	/**
	 * 출력 맵을 설정한다.
	 * 
	 * @param toMap 출력 맵
	 */
	public void setToMap(HashMap toMap) {
		// TODO: Implement when output functionality is available
	}

	/**
	 * 에러 메시지를 설정한다.
	 * 
	 * @param errorMsg 에러 메시지
	 */
	public void setErrorMsg(String errorMsg) {
		// TODO: Implement when error functionality is available
	}

	/**
	 * 호스트 서버를 반환한다.
	 * 
	 * @return 호스트 서버
	 */
	public String getHostServer() {
		// TODO: Implement when host functionality is available
		return "";
	}

	/**
	 * 호스트 AS를 반환한다.
	 * 
	 * @return 호스트 AS
	 */
	public String getHostAs() {
		// TODO: Implement when host functionality is available
		return "";
	}

	/**
	 * 키를 반환한다.
	 * 
	 * @return 키
	 */
	public String getKey() {
		// TODO: Implement when key functionality is available
		return "";
	}

	/**
	 * 메타데이터 맵을 반환한다.
	 * 
	 * @return 메타데이터 맵
	 */
	public java.util.Map getMetaDataMap() {
		// TODO: Implement when metadata functionality is available
		return new HashMap();
	}

	/*
	 * 나머지 메서드들은 주석 처리
	 */
}
