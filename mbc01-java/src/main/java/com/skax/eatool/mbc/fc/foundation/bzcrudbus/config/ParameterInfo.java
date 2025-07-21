package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * TrustForm에서 받은 파라미터 데이터와 컴포넌트 매핑 정보 관리
 * 
 * 프로그램명: ParameterInfo.java
 * 설명: 컴포넌트 매핑 정보를 로드하고 관리하는 싱글톤 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 컴포넌트 매핑 정보 로드
 * - XML 문서 관리
 * - 싱글톤 패턴으로 인스턴스 관리
 * 
 * @author 개발자
 * @version 1.0
 * @see
 * 
 */
public class ParameterInfo {

    /** 싱글톤 객체 */
    private static ParameterInfo parameterInfo; /* Singleton 객체 */

    /** 설정 파일 경로 */
    private static final String CONFIG_FILE = "/componentMappingInfo.xml"; /* 컴포넌트 매핑 정보 */

    /** 매핑 정보를 담고 있는 맵 */
    private Map mappingMap = null; /* 컴포넌트 매핑 정보가 담긴 맵 */

    /**
     * 기본 생성자
     */
    private ParameterInfo() {
        loadConfig();
    }

    /**
     * getInstance 메서드
     * 
     * @return ParameterInfo 인스턴스
     */
    public static ParameterInfo getInstance() {
        try {
            if (parameterInfo == null) {
                synchronized (ParameterInfo.class) {
                    if (parameterInfo == null) {
                        parameterInfo = new ParameterInfo();
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return parameterInfo;
    }

    /**
     * 설정 파일 로드
     * 
     */
    public void loadConfig() {
        mappingMap = Collections.synchronizedMap(new HashMap());

        try {
            InputStream inputStream = ParameterInfo.class.getResourceAsStream(CONFIG_FILE);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document componentMappingDoc = saxBuilder.build(inputStream);

            // 컴포넌트 매핑 정보 로드
            Element rootElement = componentMappingDoc.getRootElement();
            List elementList = rootElement.getChildren();
            for (int i = 0; i < elementList.size(); i++) {
                Element element = (Element) elementList.get(i);
                String fileName = element.getText();
                Document doc = getComponentDocument(fileName);
                mappingMap.put(element.getName(), doc);

            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * JDOM 객체 생성
     * 
     */
    private Document getComponentDocument(String fileName) {

        Document doc = null;

        try {
            InputStream in = ParameterInfo.class.getResourceAsStream(fileName);
            SAXBuilder saxBuilder = new SAXBuilder();
            doc = saxBuilder.build(in);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return doc;
    }

    /**
     * 컴포넌트명을 받아서 해당 컴포넌트 Document 객체를 반환한다.
     * 
     * @param componentName 컴포넌트 이름
     * @return Document 객체
     */
    public Document getDoc(String componentName) {
        return (Document) mappingMap.get(componentName);
    }

}
