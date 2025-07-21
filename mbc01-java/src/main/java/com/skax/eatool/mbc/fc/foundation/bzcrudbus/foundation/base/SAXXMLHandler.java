package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
 * SAX XML 핸들러 클래스
 * 
 * 프로그램명: SAXXMLHandler.java
 * 설명: XML 문서를 파싱하여 JDOM API의 SAXBuilder를 사용해서 Document Object로 변환한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - SAX 형태의 XML 파싱
 * - JDOM Document 객체 변환
 * - SAXBuilder 생성 및 관리
 * 
 * @version 1.0
 */
public class SAXXMLHandler extends XMLHandler {

  /**
   * SAXXMLHandler의 설정 옵션을 설정한다.
   *
   * @param option 옵션 정보를 담고 있는 HandlerOption Object
   */
  public SAXXMLHandler(HandlerOption option) {
    super(option);
  }

  /**
   * JDOM API의 SAXBuilder 객체를 생성하여 반환한다.
   *
   * @return Object SAXBuilder 객체
   */
  protected Object createBuilder() {
    System.out.println("validate " + option.getValidate());
    SAXBuilder builder = new SAXBuilder();
    return builder;
  }

  /**
   * XML 문서를 JDOM API의 Document Object로 변환하여 반환한다.
   *
   * @param xmlFile Document객체로 변환하고자 하는 File Object
   * @return Document 변환된 Document 객체
   */
  public Document parse(File xmlFile) {
    Document doc = null;
    try {
      doc = ((SAXBuilder) createBuilder()).build(xmlFile);
    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }
    return doc;
  }

  /**
   * XML 문서를 JDOM API의 Document Object로 변환하여 반환한다.
   *
   * @param xmlFileName Document객체로 변환하고자 하는 File name (path 포함)
   * @return Document 변환된 Document 객체
   */
  public Document parse(String xmlFileName) {
    Document doc = null;
    try {
      doc = ((SAXBuilder) createBuilder()).build(xmlFileName);
    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }
    return doc;
  }
}