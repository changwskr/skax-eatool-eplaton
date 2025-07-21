package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.base;

import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
 * XML 캐시 관리 클래스
 * 
 * 프로그램명: XMLCache.java
 * 설명: Doc 객체로 변환된 XML File들을 Cache로 관리하여, Document 객체로 반환한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XML 파일 캐시 관리
 * - Document 객체 반환
 * - 싱글톤 패턴 구현
 * - 캐시 초기화 및 관리
 * 
 * @version 1.0
 */
public class XMLCache {
    /** 싱글톤 인스턴스 */
    private static XMLCache instance;

    /** 핸들러 옵션 */
    private static HandlerOption option;

    /** 캐시 맵 */
    private Map docs;// Map으로 된 Cache 구조

    /**
     * 기본 생성자
     */
    private XMLCache() {
        initialize();
    }

    /**
     * 싱글톤 인스턴스를 생성하여 반환한다.
     *
     * @return XMLCache 싱글톤 인스턴스
     */
    public static synchronized XMLCache getInstance() {
        if (instance == null) {
            instance = new XMLCache();
        }
        return instance;
    }

    /**
     * Doc를 담을 HashMap을 초기화한다.
     */
    private void initialize() {
        docs = Collections.synchronizedMap(new HashMap());
        option = new HandlerOption();
        option.setValidate(true);
    }

    /**
     * Key(XML File name)에 해당하는 Doc Object를 cache에서 검색하여,
     * 있으면, 해당 Doc의 Document Object를 반환하고, 없으면, 새로
     * 파싱하여 cache에 저장하고, Document를 반환한다.
     *
     * @param key 찾고자 하는 XML File Name(path 포함)
     * @return Document 검색된 XML 파일로부터 생성된 Document Object
     */
    public Document getXML(String key) {
        return getXML(key, null);
    }

    /**
     * 유효성 검사가 포함된 XML을 반환한다.
     * 
     * @param key XML 파일명
     * @return Document Document 객체
     */
    public Document getValidXML(String key) {
        return getXML(key, option);
    }

    /**
     * Key(XML File name)에 해당하는 Doc Object를 cache에서 검색하여,
     * 있으면, 해당 Doc의 Document Object를 반환하고, 없으면, 새로
     * 파싱하여 cache에 저장하고, Document를 반환한다.
     *
     * @param key    찾고자 하는 XML File Name(path 포함)
     * @param option 새로 Doc를 생성하고자 할 때 사용할 XMLHandler의 설정 옵션
     * @return Document 검색된 XML 파일로부터 생성된 Document Object
     */
    public synchronized Document getXML(String key, HandlerOption option) {
        Doc doc = null;
        try {
            doc = (Doc) docs.get(key);
            if (doc == null) {
                doc = new Doc(key, option);
                docs.put(key, doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc.getDocument();
    }
}