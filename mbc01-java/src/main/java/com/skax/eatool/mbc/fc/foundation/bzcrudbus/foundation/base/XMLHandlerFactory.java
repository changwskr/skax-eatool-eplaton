package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

/**
 * XML 핸들러 팩토리 클래스
 * 
 * 프로그램명: XMLHandlerFactory.java
 * 설명: XMLHandler를 생성하고 관리하여 반환한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XMLHandler 객체 생성
 * - 싱글톤 패턴 구현
 * - SAX/DOM 핸들러 선택
 * 
 * @version 1.0
 */
public class XMLHandlerFactory {
  /** 싱글톤 인스턴스 */
  private static XMLHandlerFactory instance;

  /**
   * 기본 생성자
   */
  private XMLHandlerFactory() {
  }

  /**
   * 싱글톤 인스턴스를 생성하여 반환한다.
   *
   * @return XMLHandlerFactory 싱글톤 인스턴스
   */
  public static synchronized XMLHandlerFactory getInstance() {
    if (instance == null) {
      instance = new XMLHandlerFactory();
    }
    return instance;
  }

  /**
   * 옵션 조건에 따라 적절한 XMLHandler를 생성한다.
   *
   * @param handlerCondition XMLHandler 생성에 필요한 옵션
   * @return XMLHandler 생성된 XMLHandler
   */
  public XMLHandler createHandler(HandlerOption handlerCondition) {
    if (handlerCondition == null) {
      handlerCondition = new HandlerOption();
    }
    if (handlerCondition.getHandlerType() == 1) { // SAX
      return new SAXXMLHandler(handlerCondition);
    } else { // DOM
      return new DOMXMLHandler(handlerCondition);
    }
  }
}