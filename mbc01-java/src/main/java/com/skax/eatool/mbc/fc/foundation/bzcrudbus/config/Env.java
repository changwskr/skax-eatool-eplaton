
package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 환경 변수 관리 클래스
 * 
 * 프로그램명: Env.java
 * 설명: 환경 변수 및 리소스 번들에서 설정값을 가져오는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 환경 변수 조회
 * - 리소스 번들에서 설정값 조회
 * - UTF-8 인코딩 변환
 */
public class Env {

    /** 리소스 번들 이름 */
    private static final String BUNDLE_NAME = "com.skax.eatool.mbc.fc.foundation.bzcrudbus.setting.env";

    /** 리소스 번들 인스턴스 */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * 기본 생성자 (인스턴스화 방지)
     */
    private Env() {
    }

    /**
     * 환경 변수 값을 조회합니다.
     * 
     * @param key 환경 변수 키
     * @return 환경 변수 값
     */
    public static String getProperty(String key) {
        return IFRSConfig.getInstance().getInfo("EnvironmentVariable", key);
    }

    /**
     * 리소스 번들에서 설정값을 조회하고 UTF-8로 변환합니다.
     * 
     * @param key 설정 키
     * @return 설정값 (UTF-8 인코딩)
     */
    public static String getValue(String key) {

        String retStr = "";
        try {
            retStr = RESOURCE_BUNDLE.getString(key);
            if (retStr != null) {
                retStr = retStr.trim();
            }

            // ISO-8859-1에서 UTF-8로 인코딩 변환
            if (retStr != null) {
                retStr = new String(retStr.getBytes("ISO-8859-1"), "UTF-8");
            }

        } catch (MissingResourceException mre) {
            System.out.println(mre.toString());
        } catch (UnsupportedEncodingException uee) {
            System.out.println(uee.toString());
        }
        return retStr;
    }

}