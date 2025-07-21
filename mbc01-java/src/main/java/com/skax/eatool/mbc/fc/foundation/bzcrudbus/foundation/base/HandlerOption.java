package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

/**
 * XML 핸들러 옵션 클래스
 * 
 * 프로그램명: HandlerOption.java
 * 설명: XML File을 파싱할 때 사용할 옵션을 설정하기 위한 데이터 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XML 핸들러 타입 설정
 * - XML 파서 설정
 * - 유효성 검사 옵션 설정
 * 
 * @version 1.0
 */
public class HandlerOption {
  /** SAX 핸들러 타입 */
  public static final int SAX_HANDLER = 1;

  /** DOM 핸들러 타입 */
  public static final int DOM_HANDLER = 2;

  /** 핸들러 타입 */
  private int handlerType;

  /** 파서 이름 */
  private String parserName;

  /** 유효성 검사 여부 */
  private boolean validate;

  /**
   * 기본 생성자
   * XML File을 파싱할 때, 사용할 XMLHandler의 Parser, 유효성 검사 옵션을
   * 기본값으로 설정한다.
   */
  public HandlerOption() {
    this.handlerType = this.SAX_HANDLER;
    this.parserName = "org.apache.xerces.parsers.SAXParser";
    this.validate = false;
  }

  /**
   * XML File을 파싱할 때, 사용할 XMLHandler의 Parser, 유효성 검사 옵션을 설정한다.
   *
   * @param handlerType XMLHandler의 Type
   * 
   *                    <pre>
   *                     SAX_HANDLER - SAXXMLHandler default
   *                     DOM_HANDLER - DOMXMLHandler
   *                    </pre>
   * 
   * @param parserName  사용할 Parser의 Class Name
   *
   * @param validate    XML File 유효성 검사 여부
   * 
   *                    <pre>
   *                     true - 유효성 검사함
   *                     false - 유효성 검사 안함 default
   *                    </pre>
   */
  public HandlerOption(int handlerType, String parserName, boolean validate) {
    this.handlerType = handlerType;
    this.parserName = parserName;
    this.validate = validate;
  }

  /**
   * XMLHandler Type을 설정한다.
   * 
   * @param handlerType XMLHandler Type
   */
  public void setHandlerType(int handlerType) {
    this.handlerType = handlerType;
  }

  /**
   * 현재 설정된 XMLHandler Type을 반환한다.
   * 
   * @return int 현재 설정된 XMLHandler Type
   */
  public int getHandlerType() {
    return this.handlerType;
  }

  /**
   * 사용할 XML Parser를 설정한다.
   * 
   * @param parserName 사용할 XML Parser의 Class Name
   */
  public void setParserName(String parserName) {
    this.parserName = parserName;
  }

  /**
   * 현재 설정된 XML Parser의 Class Name을 반환한다.
   * 
   * @return String 현재 설정된 XML Parser의 Class Name
   */
  public String getParserName() {
    return this.parserName;
  }

  /**
   * 유효성 검사 옵션을 설정한다.
   * 
   * @param validate XML File의 유효성 검사 여부
   */
  public void setValidate(boolean validate) {
    this.validate = validate;
  }

  /**
   * 현재 설정된 유효성 검사 옵션을 반환한다.
   * 
   * @return boolean 유효성 검사 여부
   */
  public boolean getValidate() {
    return validate;
  }
}