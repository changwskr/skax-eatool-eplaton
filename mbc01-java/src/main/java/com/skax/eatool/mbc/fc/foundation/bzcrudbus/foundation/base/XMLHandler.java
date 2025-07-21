package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
 * XML 핸들러 추상 클래스
 * 
 * 프로그램명: XMLHandler.java
 * 설명: XML File을 읽기 위한 Handler들의 공통 기본 Interface를 정의한 abstract Class
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XML 파일 파싱을 위한 추상 메서드 정의
 * - SAXBuilder 또는 DOMBuilder 객체 생성
 * - File과 String 형태의 XML 파싱 지원
 * 
 * @version 1.0
 */
public abstract class XMLHandler {
  /** 핸들러 옵션 */
  protected HandlerOption option;

  /**
   * 기본 생성자
   * 
   * @param option XML File을 파싱할 때 사용할 옵션을 담고 있는 HandlerOption Object
   */
  public XMLHandler(HandlerOption option) {
    this.option = option;
  }

  /**
   * JDOM API의 SAXBuilder 또는 DOMBuilder Object를 생성한다.
   * 
   * @return Object JDOM API의 SAXBuilder 또는 DOMBuilder Object
   */
  protected abstract Object createBuilder();

  /**
   * XML 파일을 파싱하여 Document 객체를 반환한다.
   * 
   * @param xmlFile XML 파일
   * @return Document 파싱된 Document 객체
   */
  public abstract Document parse(File xmlFile);

  /**
   * XML 파일명을 받아서 파싱하여 Document 객체를 반환한다.
   * 
   * @param xmlFileName XML 파일명
   * @return Document 파싱된 Document 객체
   */
  public abstract Document parse(String xmlFileName);

}