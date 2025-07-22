package com.skax.eatool.mbc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 타입 변환 유틸리티 클래스
 * 
 * 프로그램명: TypeConverter.java
 * 설명: DTO 간 타입 변환을 위한 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - String ↔ Double 변환
 * - String ↔ Date 변환
 * - 안전한 타입 변환 처리
 * 
 * @version 1.0
 */
public class TypeConverter {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * String을 Double로 변환
     * 
     * @param value 변환할 문자열
     * @return 변환된 Double 값, 실패 시 null
     */
    public static Double stringToDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Double을 String으로 변환
     * 
     * @param value 변환할 Double 값
     * @return 변환된 문자열, null인 경우 빈 문자열
     */
    public static String doubleToString(Double value) {
        return value != null ? value.toString() : "";
    }
    
    /**
     * String을 Date로 변환
     * 
     * @param value 변환할 문자열 (yyyy-MM-dd HH:mm:ss 형식)
     * @return 변환된 Date 값, 실패 시 null
     */
    public static Date stringToDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return DATE_FORMAT.parse(value.trim());
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Date를 String으로 변환
     * 
     * @param value 변환할 Date 값
     * @return 변환된 문자열 (yyyy-MM-dd HH:mm:ss 형식), null인 경우 빈 문자열
     */
    public static String dateToString(Date value) {
        return value != null ? DATE_FORMAT.format(value) : "";
    }
    
    /**
     * 안전한 String 변환
     * 
     * @param value 변환할 값
     * @return 변환된 문자열, null인 경우 빈 문자열
     */
    public static String safeToString(Object value) {
        return value != null ? value.toString() : "";
    }
} 