package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.config;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base.XMLCache;
import org.jdom.Document;
import org.jdom.Element;

/**
 * 설정 관리 클래스
 * 
 * 프로그램명: Config.java
 * 설명: 이 클래스는 config.xml 파일을 읽어서 two level의 Node 구조로 관리하며,
 * 같은 level의 Node에서는 중복된 element Name이 존재하지 않는다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XML 설정 파일 관리
 * - 싱글톤 패턴 구현
 * - 설정 값 조회
 * - Element 객체 반환
 * 
 * @version 1.0
 */
public class Config {
  /** 설정 파일 이름 */
  private static final String CONFIG_FILE_NAME = "app.conf.directory";

  /** 싱글톤 인스턴스 */
  private static Config instance;

  /**
   * 기본 생성자
   */
  private Config() {
  }

  /**
   * 싱글톤 인스턴스를 생성하여 반환한다.
   *
   * @return Config 싱글톤 인스턴스
   */
  public static synchronized Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

  /**
   * Config.xml 파일에서 element name으로 해당 Text를 찾아서 반환한다.
   *
   * @param serviceName 찾고자 하는 1 level Element Name
   * @param elementName 찾고자 하는 2 level Element Name
   * @return String 검색된 2 level Element Text
   */
  public String getValue(String serviceName, String elementName) {
    Document doc = XMLCache.getInstance().getXML(System.getProperty(CONFIG_FILE_NAME));
    return doc.getRootElement().getChild(serviceName).getChild(elementName).getTextTrim();
  }

  /**
   * 1 level Element Name으로 Element Object를 찾아서 반환한다.
   * 2 Level이 없는 Config Data를 위해 사용한다.
   *
   * @param serviceName 찾고자 하는 1 level Element Name
   * @return Element Element 객체
   */
  public Element getElement(String serviceName) {
    Document doc = XMLCache.getInstance().getXML(System.getProperty(CONFIG_FILE_NAME));
    return doc.getRootElement().getChild(serviceName);
  }
}