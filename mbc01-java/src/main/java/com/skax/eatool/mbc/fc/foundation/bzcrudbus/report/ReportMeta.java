package com.skax.eatool.mbc.fc.foundation.bzcrudbus.report;

/**
 * 리포트 메타데이터 클래스
 * 
 * 프로그램명: ReportMeta.java
 * 설명: Word 리포트 생성을 위한 메타데이터를 담는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 리포트 기본 정보 관리 (기준년월, 리포트ID, 리포트명)
 * - 변수 정보 관리 (변수명, 변수값, 데이터타입)
 * - 그리드 데이터 타입 관리
 * - CRUD 작업 상태 관리
 * 
 * @version 1.0
 */
public class ReportMeta {

	/** 시리얼 버전 UID */
	private static final long serialVersionUID = 1L;

	/** 기준년월 */
	protected String baseYm;

	/** 리포트ID */
	protected String rptdocId;

	/** 리포트명 */
	protected String rptdocName;

	/** 순번 */
	protected String seq;

	/** 타입구분 */
	protected String fxdfmType;

	/** 변수명 */
	protected String varName;

	/** 변수값 */
	protected String varValue;

	/** 데이터타입 */
	protected String dataType;

	/** 설명 */
	protected String desc;

	/** 처리 상태 구분 */
	protected String crud;

	/**
	 * 기준년월을 반환한다.
	 * 
	 * @return 기준년월
	 */
	public String getBaseYm() {
		return baseYm;
	}

	/**
	 * 기준년월을 설정한다.
	 * 
	 * @param baseYm 기준년월
	 */
	public void setBaseYm(String baseYm) {
		this.baseYm = baseYm;
	}

	/**
	 * CRUD 상태를 반환한다.
	 * 
	 * @return CRUD 상태
	 */
	public String getCrud() {
		return crud;
	}

	/**
	 * CRUD 상태를 설정한다.
	 * 
	 * @param crud CRUD 상태
	 */
	public void setCrud(String crud) {
		this.crud = crud;
	}

	/**
	 * 데이터타입을 반환한다.
	 * 
	 * @return 데이터타입
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 데이터타입을 설정한다.
	 * 
	 * @param dataType 데이터타입
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 설명을 반환한다.
	 * 
	 * @return 설명
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 설명을 설정한다.
	 * 
	 * @param desc 설명
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 타입구분을 반환한다.
	 * 
	 * @return 타입구분
	 */
	public String getFxdfmType() {
		return fxdfmType;
	}

	/**
	 * 타입구분을 설정한다.
	 * 
	 * @param fxdfmType 타입구분
	 */
	public void setFxdfmType(String fxdfmType) {
		this.fxdfmType = fxdfmType;
	}

	/**
	 * 리포트ID를 반환한다.
	 * 
	 * @return 리포트ID
	 */
	public String getRptdocId() {
		return rptdocId;
	}

	/**
	 * 리포트ID를 설정한다.
	 * 
	 * @param rptdocId 리포트ID
	 */
	public void setRptdocId(String rptdocId) {
		this.rptdocId = rptdocId;
	}

	/**
	 * 리포트명을 반환한다.
	 * 
	 * @return 리포트명
	 */
	public String getRptdocName() {
		return rptdocName;
	}

	/**
	 * 리포트명을 설정한다.
	 * 
	 * @param rptdocName 리포트명
	 */
	public void setRptdocName(String rptdocName) {
		this.rptdocName = rptdocName;
	}

	/**
	 * 순번을 반환한다.
	 * 
	 * @return 순번
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * 순번을 설정한다.
	 * 
	 * @param seq 순번
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
	 * 변수명을 반환한다.
	 * 
	 * @return 변수명
	 */
	public String getVarName() {
		return varName;
	}

	/**
	 * 변수명을 설정한다.
	 * 
	 * @param varName 변수명
	 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/**
	 * 변수값을 반환한다.
	 * 
	 * @return 변수값
	 */
	public String getVarValue() {
		return varValue;
	}

	/**
	 * 변수값을 설정한다.
	 * 
	 * @param varValue 변수값
	 */
	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}
}
