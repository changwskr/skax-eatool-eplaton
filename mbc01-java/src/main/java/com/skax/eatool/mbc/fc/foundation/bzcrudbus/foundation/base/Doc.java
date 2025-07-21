package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

import java.io.*;
import org.jdom.*;

/**
 * XML 문서 관리 클래스
 * 
 * 프로그램명: Doc.java
 * 설명: XML 파일을 Document 객체로 관리하고 파일 변경을 감지한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XML 파일을 Document 객체로 관리
 * - 파일 변경 감지
 * - Document 초기화
 * - 파일 수정 시간 조회
 * 
 * @version 1.0
 */
public class Doc {
    /** Document 객체 */
    private Document doc;

    /** 마지막 수정 시간 */
    private long lastModified;

    /** 문서 파일명 */
    private final String docFileName;

    /** 핸들러 옵션 */
    private HandlerOption option;

    /**
     * 파싱하고자 하는 XML 파일과 관련 설정을 받아서 Document 초기화를 수행한다.
     *
     * @param docFileName      파싱하고자 하는 XML 파일명
     * @param handlerCondition Document 파싱에 사용하고자 하는 코드 옵션
     */
    public Doc(String docFileName, HandlerOption handlerCondition) {
        this.option = handlerCondition;
        this.docFileName = docFileName;
        this.lastModified = getLastModified();
        documentInit();
    }

    /**
     * 설정된 옵션에 따라 XMLHandlerFactory에서
     * XMLHandler를 생성하고, XML 파일을 Document로 변환한다.
     */
    private void documentInit() {
        XMLHandler handler = XMLHandlerFactory.getInstance().createHandler(option);
        doc = handler.parse(docFileName);
        System.out.println("document init...");
    }

    /**
     * 파일이 변경되었는지 확인하여, 변경되었다면
     * Document를 다시 파싱하고, Document를 반환한다.
     *
     * @return Document 반환된 JDOM API의 Document Object
     */
    public Document getDocument() {
        if (isModified())
            documentInit();
        return doc;
    }

    /**
     * 파일의 변경 여부를 반환한다.
     *
     * @return boolean 해당 파일의 변경 여부
     * 
     *         <pre>
     *   - 변경되지 않았으면 false
     *   - 변경되었으면 true
     *         </pre>
     */
    private boolean isModified() {
        try {
            // System.out.println("lastModified:" + lastModified);
            // System.out.println("getLastModified:" + getLastModified());
            if (lastModified != getLastModified()) {
                lastModified = getLastModified();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 파일의 마지막 수정 시간을 반환한다.
     *
     * @return long 해당 파일의 마지막 수정 시간
     */
    private long getLastModified() {
        try {
            File file = new File(docFileName);
            return file.lastModified();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}