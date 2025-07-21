package com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer;

import java.util.HashMap;
import java.util.Map;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Reflector;

/**
 * DTO 추상 클래스
 * 
 * 프로그램명: DTO.java
 * 설명: 우리 시스템에서 DTO를 상속받는 class가 반드시 구현해야 할 공통 method를 정의한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - DTO 속성 관리
 * - 버전 관리
 * - 객체 문자열 변환
 * - DTO 속성 조회 및 설정
 * 
 * @version 1.30
 */
public abstract class DTO implements IDTO {
    /** DTO 속성 맵 */
    private Map dtoProperties;

    /** 버전 */
    private String version;

    /**
     * 기본 생성자
     * 
     * @see java.lang.Object#Object()
     */
    public DTO() {
    }

    /**
     * 객체를 문자열로 변환한다.
     * 
     * @see java.lang.Object#toString()
     * @return 객체의 문자열 표현
     */
    public String toString() {
        return Reflector.objectToString(this);
    }

    /**
     * DTO 속성 맵을 반환한다.
     * 
     * @return DTO 속성 맵
     */
    private Map getDTOProperties() {
        if (dtoProperties == null) {
            dtoProperties = new HashMap(2);
        }
        return dtoProperties;
    }

    /**
     * DTO 속성을 모두 지운다.
     */
    public final void clearDTOProperty() {
        if (dtoProperties != null) {
            dtoProperties.clear();
        }
    }

    /**
     * DTO 속성을 반환한다.
     * 
     * @param key 속성 키
     * @return 속성 값
     */
    public final Object getDTOProperty(Object key) {
        if (dtoProperties == null) {
            return null;
        } else {
            return getDTOProperties().get(key);
        }
    }

    /**
     * DTO 속성을 설정한다.
     * 
     * @param key   속성 키
     * @param value 속성 값
     */
    public final void putDTOProperty(Object key, Object value) {
        getDTOProperties().put(key, value);
    }

    /**
     * 버전을 설정한다.
     * 
     * @param version 버전
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 버전을 반환한다.
     * 
     * @return 버전
     */
    public String getVersion() {
        return this.version;
    }
}
