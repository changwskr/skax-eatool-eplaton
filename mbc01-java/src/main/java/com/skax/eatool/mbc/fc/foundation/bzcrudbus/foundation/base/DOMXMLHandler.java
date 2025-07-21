package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
 * DOM XML 핸들러 클래스
 * 
 * 프로그램명: DOMXMLHandler.java
 * 설명: JDOM API의 DOMBuilder를 사용하여 DOM 형태의 Document Object로 변환한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - DOM 형태의 XML 파싱
 * - JDOM Document 객체 변환
 * - DOMBuilder 생성 및 관리
 * 
 * @version 1.0
 */
public class DOMXMLHandler extends XMLHandler {

    /**
     * DOMXMLHandler의 설정 옵션을 설정한다.
     *
     * @param option 옵션 정보를 담고 있는 HandlerOption Object
     */
    public DOMXMLHandler(HandlerOption option) {
        super(option);
    }

    /**
     * 구현되지 않은 Method
     * 항상 null을 return 한다
     *
     * @param xmlFile XML 파일
     * @return Document
     */
    public Document parse(File xmlFile) {
        return null;
    }

    /**
     * 구현되지 않은 Method
     * 항상 null을 return 한다
     *
     * @param xmlFileName XML 파일명
     * @return Document
     */
    public Document parse(String xmlFileName) {
        return null;
    }

    /**
     * XML 문서를 JDOM API의 Document Object로 변환하여 반환한다.
     *
     * @param document DOM Document객체로 변환하고자 하는 Document Object
     * @return Document 변환된 Document 객체
     */
    public Document parse(org.w3c.dom.Document document) {
        Document doc = ((DOMBuilder) createBuilder()).build(document);
        return doc;
    }

    /**
     * JDOM API의 DOMBuilder 객체를 생성하여 반환한다.
     *
     * @return Object DOMBuilder 객체
     */
    protected Object createBuilder() {
        DOMBuilder builder = new DOMBuilder();
        return builder;
    }
}