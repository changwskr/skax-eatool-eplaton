package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import java.util.HashMap;

/**
 * 설정 정보를 담는 Value Object
 * 
 * 프로그램명: ConfigInfo.java
 * 설명: 시스템 설정 정보를 저장하는 데이터 객체
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 시스템 설정 정보 저장
 * - 설정 정보 조회 및 설정
 * 
 * @author : jws
 * @version : 1.0
 */
public class ConfigInfo {
    /** 시스템 설정 맵 */
    private HashMap sysConfig;

    /**
     * 시스템 설정 맵을 반환한다.
     * 
     * @return 시스템 설정 맵
     */
    public HashMap getSysConfig() {
        return sysConfig;
    }

    /**
     * 시스템 설정 맵을 설정한다.
     * 
     * @param map 시스템 설정 맵
     */
    public void setSysConfig(HashMap map) {
        sysConfig = map;
    }

}
