/*
 * Copyright (c) 2001-2002 IMS System CO., LTD.
 * SAMAN BLDG, 5F, 945 DAECHI-DONG KANGNAM-GU, SEOUL, KOREA.
 * All rights reserved.
 */

package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 문자열 처리 유틸리티 클래스
 * 
 * 프로그램명: StringUtils.java
 * 설명: String 처리를 위한 helper function들입니다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team (원작자: Gwanghyeok Yu (ghyu@imssystem.com))
 * 
 * 주요 기능:
 * - 문자열 비교 및 검증
 * - 문자열 변환 및 포맷팅
 * - 문자열 분할 및 결합
 * - 문자열 치환 및 삽입
 * - 문자열 패딩 및 정렬
 * - 정규식 처리
 * 
 * @version 1.0
 */
public final class StringUtils {

    /** 왼쪽 정렬 상수 */
    public static final int LEFT = 1;

    /** 가운데 정렬 상수 */
    public static final int CENTER = 2;

    /** 오른쪽 정렬 상수 */
    public static final int RIGHT = 3;

    /** 인스턴스 생성 불가 */
    private StringUtils() {
    }

    // ------------------------------------------------------------ base

    /**
     * 두 문자열이 같은지 확인한다.
     *
     * <blockquote>
     * 
     * <pre>
     * boolean b = StringUtil.equals(null, null); // true
     * </pre>
     * 
     * </blockquote>
     *
     * @param str1 첫 번째 String
     * @param str2 두 번째 String
     * @return 같은 문자열이면 <code>true</code>를 반환하고 다르면
     *         <code>false</code>를 반환한다. 둘 다 null이면
     *         <code>false</code>가 된다.
     */
    public static boolean equals(String str1, String str2) {
        return (null == str1 ? null == str2 : str1.equals(str2));
    }

    /**
     * 문자열이 비어있는지 확인한다.
     *
     * <blockquote>
     * 
     * <pre>
     * boolean b = StringUtil.isEmpty("  "); // true
     * String str = null;
     * beolean b1 = StringUtil.isEmpty(str); // true
     * </pre>
     * 
     * </blockquote>
     *
     * @param str 확인하고자 하는 문자열
     * @return 문자열이 비어있으면 <code>true</code>를 반환하고 비어있지 않으면
     *         <code>false</code>를 반환한다. 문자열이 <code>null</code>이거나
     *         trim한 후 길이가 <code>0</code>이면 빈 문자열로 간주한다.
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 문자열의 길이가 1이상이면 true이다. 이때 문자열은
     * java.lang.String.trim()한 후의 길이를 의미한다.
     *
     * <blockquote>
     * 
     * <pre>
     * boolean b = StringUtil.isValid("  "); // true
     * String str = null;
     * beolean b1 = StringUtil.isEmpty(str); // false
     * </pre>
     * 
     * </blockquote>
     *
     * @see #isValid(String, int)
     */
    public static boolean isValid(String str) {
        return isValid(str, 1);
    }

    /**
     * 문자열이 특정 길이 이상이면 true를 반환한다. 이때 문자열은
     * java.String.trim()한 후의 길이를 의미한다.
     *
     * @param str 확인하고자 하는 문자열
     * @param len 확인하고자 하는 길이
     * @return 문자열이 비어있으면 <code>true</code>를 반환하고 비어있지 않으면
     *         <code>false</code>를 반환한다. 문자열이 <code>null</code>이면
     *         <code>false</code>를 반환한다
     */
    public static boolean isValid(String str, int len) {
        return (str != null && str.length() >= len);
    }

    /**
     * 영문코드 문자만으로 구성되어 있는지 확인한다.
     *
     * @return 문자만이면 <code>true</code>를 반환하고 아니면
     *         <code>false</code>를 반환한다. 문자열이 <code>null</code>이면
     *         <code>false</code>를 반환한다
     */
    public static boolean isWord(String str) {
        if (null == str)
            return false;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 영문코드 문자나 숫자로만 구성되어 있는지 확인한다.
     *
     * @return 문자나 숫자만이면 <code>true</code>를 반환하고 아니면
     *         <code>false</code>를 반환한다. 문자열이 <code>null</code>이면
     *         <code>false</code>를 반환한다
     */
    public static boolean isAlphanumeric(String str) {
        if (null == str)
            return false;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 영문코드 숫자로만 구성되어 있는지 확인한다.
     *
     * @return 숫자만이면 <code>true</code>를 반환하고 아니면
     *         <code>false</code>를 반환한다. 문자열이 <code>null</code>이면
     *         <code>false</code>를 반환한다
     */
    public static boolean isNumeric(String str) {
        if (null == str)
            return false;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * reqular expression의 meta sybmol을 quotation한다. 문자열이 null이면
     * null을 반환한다.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.quoteRegularExpression("^a[bc]");
     * // output --> "\^a\[bc\]"
     * </pre>
     * 
     * </blockquote>
     */
    public static String quoteRegularExpression(String str) {
        if (null == str)
            return null;
        char[] chars = str.toCharArray();
        int len = chars.length;
        StringBuffer sb = new StringBuffer(2 * len);
        for (int i = 0; i < len; i++) {
            switch (chars[i]) {
                case '[':
                case ']':
                case '?':
                case '+':
                case '*':
                case '/':
                case '.':
                case '^':
                case '$':
                    sb.append("\\");
                default:
                    sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    // ------------------------------------------------------------ count
    public static int getWordCount(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        return tokenizer.countTokens();
    }

    public static int getWordCount(String str, String delim) {
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        return tokenizer.countTokens();
    }

    // -------------------------------------------------------------- padding

    /**
     * java.lang.String.trim()�� ���ڿ��� <code>null</code>�� ���
     * <code>NullPointException</code> �������� �� �޼ҵ��
     * <codE>null</code>�� �����Ѵ�
     *
     * @see #clean(String str)
     */
    public static String trim(String str) {
        return trim(str, null);
    }

    /**
     * java.lang.String.trim()�� ���� ������ �������ְ� ���ڿ��� null��
     * ���� �� ���ڿ��� �����Ѵ�.
     */
    public static String clean(String str) {
        return trim(str, "");
    }

    /**
     * ���ڿ��� ������ �����Ѵ�. ���ڿ��� null�̶�� default value��
     * �����Ѵ�.
     *
     * @param str trim�ϰ��� �ϴ� string�� ��Ÿ����
     * @param def default value�� ��Ÿ����
     */
    public static String trim(String str, String def) {
        return (null == str ? def : str.trim());
    }

    /**
     * ���� string�� null�̶�� ���ڿ��� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.parseString(null);
     * // output --> ""
     * </pre>
     * 
     * </blockquote>
     *
     * @see #parseString(String, String)
     */
    public static String parseString(String str) {
        return parseString(str, "");
    }

    /**
     * ���� String�� null�̶�� default value�� �����Ѵ�
     */
    public static String parseString(String str, String defaultValue) {
        return (str == null ? defaultValue : str);
    }

    /**
     * ������ ������ �����Ѵ�. ���� ���ڿ��� null�̶�� null�� �����Ѵ�.
     *
     * @see #stripStart(String, String)
     */
    public static String ltrim(String str) {
        return stripStart(str, null);
    }

    /**
     * ������ ������ �����Ѵ�. ���� ���ڿ��� null�̶�� null�� �����Ѵ�.
     *
     * @see #stripEnd(String, String)
     */
    public static String rtrim(String str) {
        return stripEnd(str, null);
    }

    /**
     * padding�� ��Ÿ����.
     *
     * @param direction ����, ������, ����� ��Ÿ����. ���� LEFT, CENTER, RIGHT
     *                  ������ Ʋ���� �ܼ��� string�� �����Ѵ�
     * @see #left(String, int, String)
     * @see #center(String, int, String)
     * @see #right(String, int, String)
     */
    public static String padding(String str, int n, String delim,
            int direction) {
        if (direction == LEFT)
            return left(str, n, delim);
        else if (direction == CENTER)
            return center(str, n, delim);
        else if (direction == RIGHT)
            return right(str, n, delim);
        return str;
    }

    /**
     * Ư�� ���̺��� ���ڿ��� ���̰� �۴ٸ� ������ ������ �־��ش�.
     *
     * @see #left(String, int, String)
     */
    public static String left(String str, int n) {
        return left(str, n, ' ');
    }

    /**
     * ���ڿ��� Ư�� ���̺��� �۴ٸ� ������ delimiter ���ڸ� ������ �ش�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.left("abcd", 6, ' '); // output --> " abcd"
     * </pre>
     * 
     * </blockquote>
     *
     * @see #left(String, int, String)
     */
    public static String left(String str, int n, char delim) {
        return left(str, n, delim + "");
    }

    /**
     * ���ڿ��� Ư�� ���̺��� �۴ٸ� ������ delimiter�� ������ �ش�. ���ڿ���
     * null�̶�� delimiter�� ��� ä�� �Ŀ� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = Stringutil.left("1234", 7, "%&");
     * // output --> "%&1234"
     * </pre>
     * 
     * </blockquote>
     */
    public static String left(String str, int n, String delim) {
        if (null == str)
            str = "";
        int len = str.length();
        n = (n - len) / delim.length();
        if (n > 0) {
            StringBuffer sb = new StringBuffer(len + n);
            repeat(sb, delim, n);
            sb.append(str);
            return sb.toString();
        } else {
            return str;
        }
    }

    /**
     * Ư�� ���̺��� string�� ���̰� �۴ٸ� ������ ������ �־��ش�.
     *
     * @see #left(String, int, String)
     */
    public static String right(String str, int n) {
        return right(str, n, ' ');
    }

    /**
     * ���ڿ��� Ư�� ���̺��� �۴ٸ� ������ delimiter ���ڸ� �־��ش�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.right("abcd", 6, ' '); // output --> "abcd "
     * </pre>
     * 
     * </blockquote>
     *
     * @see #right(String, int, String)
     */
    public static String right(String str, int n, char delim) {
        return right(str, n, delim + "");
    }

    /**
     * ���ڿ��� Ư�� ���̺��� �۴ٸ� ������ delimiter ���ڸ� �־��ش�.
     * ���ڿ��� null�̶�� delimiter�� ��� ä�� �Ŀ� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = Stringutil.right("1234", 7, "%&");
     * // output --> "1234%&"
     * </pre>
     * 
     * </blockquote>
     */
    public static String right(String str, int n, String delim) {
        if (null == str)
            str = "";
        int len = str.length();
        n = (n - str.length()) / delim.length();
        if (n > 0) {
            StringBuffer sb = new StringBuffer(n + len);
            sb.append(str);
            repeat(sb, delim, n);
            return sb.toString();
        } else {
            return str;
        }
    }

    /**
     * Ư�� ���̺��� string�� ���̰� �۴ٸ� �¿쿡 ������ �־��ش�.
     *
     * @see #left(String, int, String)
     */
    public static String center(String str, int n) {
        return center(str, n, ' ');
    }

    /**
     * ���ڿ��� Ư�� ���̺��� �۴ٸ� �¿쿡 delimiter ���ڸ� ������ �ش�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.center("abcd", 6, ' '); // output --> " abcd "
     * String str = StringUtil.center("abcd", 7, ' '); // output --> " abcd "
     * </pre>
     * 
     * </blockquote>
     */
    public static String center(String str, int n, char delim) {
        return center(str, n, delim + "");
    }

    /**
     * ���ڿ��� Ư�� ���̺��� �۴ٸ� �¿쿡 delimiter�� ������ �ش�.
     * ���� ���鿡 �̵���� �ȵȴٸ� right�� ä���. ���ڶ�� ä���� �ʰ� ���� �д�.
     * ���ڿ��� null�̶�� delimiter�� ���� ä�� �Ŀ� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = Stringutil.center("1234", 7, "%&");
     * // output --> "1234%&"
     * </pre>
     * 
     * </blockquote>
     */
    public static String center(String str, int n, String delim) {
        if (null == str)
            str = "";
        int len = str.length();
        int i = (n - str.length()) / delim.length();
        if (i > 0) {
            str = left(str, len + i / 2, delim);
            str = right(str, n, delim);
        }
        return str;
    }

    // ------------------------------------------------------------- replacement

    /**
     * Ư�� ���ڸ� Ư�� ���ڿ��� �ٲ��ݴϴ�.
     *
     * ���� ���, Ư�� ���ڸ� escape ó���ϰ��� �� �� �̿��� �� �ִ�.<br>
     * <blockquote>
     * 
     * <pre>
     * pstmt = con.prepareStatement(...);
     * pstmt.setString(1, SringUtil.replace("IMS'S HOPE", '\'', "''"));
     * </pre>
     * 
     * </blockquote>
     *
     * @param source  ���� ���ڿ��̴�.
     * @param ch      �ٲٰ��� �ϴ� �����̴�.
     * @param replace ��ġ�ϰ��� �ϴ� ���ڿ��̴�.
     *
     * @see #replace(String source, char ch, String replace, int max)
     */
    public static String replace(String source, char ch,
            String replace) {
        return replace(source, ch, replace, -1);
    }

    /**
     * Ư�� ���ڸ� Ư�� ���ڿ��� �ٲ��ݴϴ�.
     *
     * @param source  ���� ���ڿ��̴�.
     * @param ch      �ٲٰ��� �ϴ� �����̴�.
     * @param replace ��ġ�ϰ��� �ϴ� ���ڿ��̴�.
     * @param max     ��� �ٲ� �������� ��Ÿ����. <code>-1</code>�̸�
     *                ��� �ٲ۴�
     *
     * @see #replace(String source, String original, String replace, int max)
     */
    public static String replace(String source, char ch,
            String replace, int max) {
        return replace(source, ch + "", replace, max);
    }

    /**
     * Ư�� ���ڿ��� Ư�� ���ڿ��� �ٲ��ݴϴ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.replace("Java \r\n is \r\n Wonderful",
     *         "\r\n", "<BR>");
     * </pre>
     * 
     * </blockquote>
     *
     * @param source   ���� ���ڿ��̴�.
     * @param original �ٲٰ��� �ϴ� ���ڿ��Դϴ�.
     * @param replace  ��ġ�ϰ��� �ϴ� ���ڿ��̴�.
     */
    public static String replace(String source, String original,
            String replace) {
        return replace(source, original, replace, -1);
    }

    /**
     * Ư�� ���ڿ��� Ư�� ���ڿ��� �ٲ��ݴϴ�. ���ڿ��� null�̶�� null�� �����Ѵ�.
     *
     * @param source   ���� ���ڿ��̴�.
     * @param original �ٲٰ��� �ϴ� ���ڿ��Դϴ�.
     * @param replace  ��ġ�ϰ��� �ϴ� ���ڿ��̴�.
     * @param max      ��� �ٲܰ������� ��Ÿ����. <code>-1</code>�̸�
     *                 ��� �ٲ۴�.
     */
    public static String replace(String source, String original,
            String replace, int max) {
        if (null == source)
            return null;
        int nextPos = 0; // ���� position
        int currentPos = 0; // ���� position
        int len = original.length();
        StringBuffer result = new StringBuffer(source.length());
        while ((nextPos = source.indexOf(original, currentPos)) != -1) {
            result.append(source.substring(currentPos, nextPos));
            result.append(replace);
            currentPos = nextPos + len;
            if (--max == 0) { // �ٲ� Ƚ���� �پ��ش�
                break;
            }
        }
        if (currentPos < source.length()) {
            result.append(source.substring(currentPos));
        }
        return result.toString();
    }

    /**
     * String�� Ư�� ������ �ٸ� ���ڷ� ��ġ�Ѵ�. ���ڿ��� null�̶��
     * null�� �����ϴ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.overlay("Java", 'V', 2, 3);
     * // output --> "JaVa"
     * </pre>
     * 
     * </blockquote>
     *
     * @param ch    ��ġ�ϰ��� �ϴ� ���ڸ� ��Ÿ����
     * @param start ��ġ�ϰ��� ���ڿ��� ���� index�� ��Ÿ����
     * @param start ��ġ�ϰ��� ���ڿ��� ���� index�� ��Ÿ����
     */
    public static String overlay(String str, char ch, int start, int end) {
        return overlay(str, ch + "", start, end);
    }

    /**
     * String�� Ư�� ������ �ٸ� ���ڿ����� ��ġ�Ѵ�. ���ڿ��� null�̶��
     * null�� �����ϴ�.
     *
     * @exception java.lang.IndexOutOfBoundsException start, end�� index��
     *                                                ������ ����� �߻��Ѵ�
     */
    public static String overlay(String str, String overlay,
            int start, int end) {
        if (null == str)
            return null;
        String pre = str.substring(0, start);
        String post = str.substring(end);
        return pre + overlay + post;
    }

    /**
     * String�� Ư�� key�� ������ �ٲ۴�. ���� text�� null�̶�� null�� �����ϰ�
     * map�� null�̶�� ���� ���ڿ��� �����Ѵ�. ���� ��� xml���� Ư�� �Ӽ���
     * �о String�� �κ��� �ٱ����� �Ҷ� �̿��Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String text = "${ip} ${port}";
     * Map map = HashMap();
     * map.put("ip", "203.252.157.66");
     * map.put("port", "2002");
     * String str = StringUtil.interpolate(text, map);
     * </pre>
     * 
     * </blockquote>
     */
    public static String interpolate(String text, Map map) {
        if (null == text)
            return null;
        if (null == map)
            return text;
        Iterator keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            String value = map.get(key).toString();
            text = replace(text, "${" + key + "}", value);
            /*
             * if (key.indexOf(" ") == -1) {
             * text = replace(text, "$" + key, value);
             * }
             */
        }
        return text;
    }

    // ----------------------- replacement/case

    /**
     * ù ���ڸ� �빮�ڷ� �ٲپ� �ش�. �Է� String�� null�̶�� null��
     * �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.toFirstUpperCase("java"); // result --> "Java"
     * </pre>
     * 
     * </blockquote>
     */
    public static String toFirstUpperCase(String str) {
        return (null == str ? null : str.substring(0, 1).toUpperCase() + str.substring(1));
    }

    /**
     * Ư�� ���̸�ŭ ���ڸ� �빮�ڷ� �ٲپ� �ش�. �Է� String�� null�̶�� null
     * �� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.toUpperCase("java", 4) // result -> "JAVA"
     * </pre>
     * 
     * </blockquote>
     */
    public static String toUpperCase(String str, int len) {
        if (null == str)
            return null;
        int strLen = str.length();
        int index = 0;
        StringBuffer sb = new StringBuffer(str.length());
        while ((index < len) && (index < strLen)) {
            sb.append(Character.toUpperCase(str.charAt(index)));
            ++index;
        }
        if (index < strLen) {
            sb.append(str.substring(index));
        }
        return sb.toString();
    }

    /**
     * Character.toTitleCase()�� �̿��Ͽ� �빮�� �Ѵ�
     *
     * @see #toUpperCase(String, int)
     */
    public static String toTitleCase(String str, int len) {
        if (null == str)
            return null;
        int strLen = str.length();
        int index = 0;
        StringBuffer sb = new StringBuffer(str.length());
        while ((index < len) && (index < strLen)) {
            sb.append(Character.toTitleCase(str.charAt(index)));
            ++index;
        }
        if (index < strLen) {
            sb.append(str.substring(index));
        }
        return sb.toString();
    }

    /**
     * ù ���ڸ� �ҹ��ڷ� �ٲپ� �ش�. �Է� String�� null�̶��
     * null�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.toFirstLowerCase("Java"); // result --> "java"
     * </pre>
     * 
     * </blockquote>
     */
    public static String toFirstLowerCase(String str) {
        return (null == str ? null : str.substring(0, 1).toLowerCase() + str.substring(1));
    }

    /**
     * Ư�� ���̸�ŭ ���ڸ� �ҹ��ڷ� �ٲپ� �ش�. �Է� String�� null�̶�� null
     * �� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.toLowerCase("JAVA", 4) // result -> "java"
     * </pre>
     * 
     * </blockquote>
     */
    public static String toLowerCase(String str, int len) {
        if (null == str)
            return null;
        int strLen = str.length();
        int index = 0;
        StringBuffer sb = new StringBuffer(str.length());
        while ((index < len) && (index < strLen)) {
            sb.append(Character.toLowerCase(str.charAt(index)));
            ++index;
        }
        if (index < strLen) {
            sb.append(str.substring(index));
        }
        return sb.toString();
    }

    /**
     * ���ڿ��� ��ҹ��ڸ� �ٲپ� �ش�. ���ڿ��� null�̶�� null�� �����Ѵ�
     */
    public static String swapCase(String str) {
        if (null == str)
            return null;
        int size = str.length();
        StringBuffer sb = new StringBuffer(size);

        char ch = 0;
        char tmp = 0;
        boolean whitespace = false;
        for (int i = 0; i < size; i++) {
            ch = str.charAt(i);
            if (Character.isUpperCase(ch) || Character.isTitleCase(ch)) {
                tmp = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                if (whitespace) {
                    tmp = Character.toTitleCase(ch);
                } else {
                    tmp = Character.toUpperCase(ch);
                }
            }
            sb.append(tmp);
            whitespace = Character.isWhitespace(ch);
        }
        return sb.toString();
    }

    /**
     * ���� string�� �Ųٷ� �ٲپ� �ش�. null�̶�� null�� �����Ѵ�
     */
    public static String reverse(String str) {
        if (null == str)
            return null;
        return new StringBuffer(str).reverse().toString();
    }

    // ------------------------------------------------------------------- split

    /**
     * String�� �������� �и����� ���ڿ��� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String[] strs = StringUtil.split("Java is wonderful");
     * // strs[0] = "Java"; strs[1] = "is"; strs[2] = "wonderful"
     * </pre>
     * 
     * </blockquote>
     *
     * #see split(String str, String delim)
     */
    public static String[] split(String str) {
        return split(str, " ", -1);
    }

    /**
     * String�� Ư�� ���ڷ� �и��Ͽ� ���ڿ� �迭�� �����Ѵ�.
     *
     * #see split(String str, char delim, int max)
     */
    public static String[] split(String str, char delim) {
        return split(str, delim + "", -1);
    }

    /**
     * String�� Ư�� ���ڷ� �и��Ͽ� ���ڿ� �迭�� �����Ѵ�.
     *
     * @see #split(String str, String delim, int max)
     */
    public static String[] split(String str, char delim, int max) {
        return split(str, delim + "", max);
    }

    /**
     * String�� Ư�� ���ڿ��� �и����� ���ڿ� �迭�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String[] strs = StringUtil.split("a=b&b=c&c=d", "&");
     * // strs[0] = "a=b"; strs[1] = "b=c"; strs[2] = "c=d"
     * </pre>
     * 
     * </blockquote>
     *
     * @see #split(String str, String delim, int max)
     * @see #tokenize(String str, String delims)
     */
    public static String[] split(String str, String delim) {
        return split(str, delim, -1);
    }

    /**
     * String�� Ư�� ���ڿ��� Ư�� Ƚ����ŭ �и��ؼ� ���ڿ��� �����Ѵ�.
     *
     * @param str   �и��ϰ��� �ϴ� ���ڿ��� ��Ÿ����.
     * @param delim �и��ڸ� ��Ÿ����.
     * @param max   �и� Ƚ���� ��Ÿ����. <code>-1</code>��
     *              ��ü�� �и��Ѵ�.
     */
    public static String[] split(String str, String delim, int max) {
        int nextPos = 0;
        int currentPos = 0;
        int len = delim.length();
        List list = new ArrayList();
        while ((nextPos = str.indexOf(delim, currentPos)) != -1) {
            list.add(str.substring(currentPos, nextPos));
            currentPos = nextPos + len;
            if (--max == 0) { // �и� Ƚ���� �پ��ش�
                break;
            }
        }
        if (currentPos < str.length()) {
            list.add(str.substring(currentPos));
        }
        return (String[]) list.toArray(new String[0]);
    }

    /**
     * String�� Ư�� character���� �̿��Ͽ� �и����� ���ڿ��� �����Ѵ�.
     * {@link #split(String, String)} �޼ҵ�� �ٸ��� ��������
     * delimeter character�� �޾� tokenizing�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String[] strs = StringUtil.split("a=b&b=c&c=d", "=&");
     * // strs[0] = "a"; strs[1] = "b"; strs[2] = "c"; ... strs[5] = "d";
     * </pre>
     * 
     * </blockquote>
     *
     * @see #tokenize(String str, String delims, int max)
     * @see #split(String, String)
     */
    public static String[] tokenize(String str, String delims) {
        return tokenize(str, delims, -1);
    }

    /**
     * ���ڿ��� Ư�� character���� �̿��Ͽ� �и����� ���ڿ� �迭�� �����Ѵ�.
     *
     * @param delims �����ڵ��� ��Ÿ����. ���� �����̰� ���� ���ڰ� �����ϴ�.
     * @param max    �и� Ƚ���� ��Ÿ����. <code>-1</code>��
     *               ��ü�� �и��Ѵ�.
     */
    public static String[] tokenize(String str, String delims, int max) {
        StringTokenizer st = new StringTokenizer(str, delims);
        int size = st.countTokens();
        if (max != -1 && size > max) {
            size = max;
        }
        String[] list = new String[size];
        int i = 0;
        while (st.hasMoreTokens()) {
            if ((max != -1) && (i == size - 1)) {
                break;
            } else {
                list[i] = st.nextToken();
            }
            i++;
        }
        return list;
    }

    /**
     * String�� Ư�� ���ڿ��� �и��� List �����Ѵ�.
     *
     * @see #splitAsList(str, delim, max)
     */
    public static List splitAsList(String str, String delim) {
        return splitAsList(str, delim, -1);
    }

    /**
     * String�� Ư�� ���ڿ��� Ư�� Ƚ����ŭ �и��� List �����Ѵ�.
     *
     * @param str   �и��ϰ��� �ϴ� ���ڿ��� ��Ÿ����
     * @param delim �и��ڸ� ��Ÿ����.
     * @param max   �и� Ƚ���� ��Ÿ����. <code>-1</code>��
     *              ��ü�� �и��Ѵ�.
     */
    public static List splitAsList(String str, String delim, int max) {
        String[] strs = split(str, delim, max);
        return Arrays.asList(strs);
    }

    // -------------------------------------------------------------------- join

    /**
     * ��ü �迭�� element���� Ư�� " "�� �̿��Ͽ� ��� �����Ų��.
     * ��ü �迭�� null�̸� null�� �����Ѵ�
     *
     * @see #join(Object[] list, String delim)
     */
    public static String join(Object[] list) {
        return join(list, " ");
    }

    /**
     * ��ü �迭�� element���� Ư�� delimiter ���ڸ� �̿��Ͽ� ��� �����Ų��.
     * ��ü �迭�� null�̸� null�� �����Ѵ�
     *
     * @see #join(Object[] list, String delim)
     */
    public static String join(Object[] list, char delim) {
        return join(list, delim + "");
    }

    /**
     * collection�� element���� Ư�� delimiter ���ڸ� �̿��Ͽ� ��� �����Ų��.
     * iterator�� null�̸� null�� �����ϰ� element�� ���� ���� ������ ""��
     * �����Ѵ�.
     *
     * @see #join(Iterator iterator, String delim)
     */
    public static String join(Iterator iterator, char delim) {
        return join(iterator, delim + "");
    }

    /**
     * ��ü �迭�� element���� Ư�� delimiter ���ڿ��� �̿��Ͽ� ��� �����Ų��.
     * ��ü �迭�� null�̸� null�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * Strings[] strs = new Strings[] { "a=b", "b=c", "c=d" };
     * String str = StringUtil.join(strs, "&"); // result --> "a=b&b=c&c=d"
     * </pre>
     * 
     * </blockquote>
     */
    public static String join(Object[] list, String delim) {
        if (null == list)
            return null;
        int size = list.length;
        int len = (size == 0 ? 0
                : (list[0].toString().length() +
                        delim.length()) * size);
        StringBuffer sb = new StringBuffer(len);
        if (list.length > 0)
            sb.append(list[0].toString());
        for (int i = 1; i < size; i++) {
            sb.append(delim);
            sb.append(list[i]);
        }
        return sb.toString();
    }

    /**
     * collection�� element���� Ư�� delimiter ���ڸ� �̿��Ͽ� ��� �����Ų��.
     * iterator�� null�̸� null�� �����ϰ� element�� ���� ���� ������ ""��
     * �����Ѵ�.
     */
    public static String join(Iterator iterator, String delim) {
        if (null == iterator)
            return null;
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * pattern�� Ư�� Ƚ����ŭ �ݺ��ؼ� �����ش�. pattern�� null�̸� null��
     * �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.repeat("1", 5);
     * // output --> 11111
     * </pre>
     * 
     * </blockquote>
     */
    public static String repeat(String pattern, int n) {
        if (null == pattern)
            return null;
        StringBuffer sb = new StringBuffer(n * pattern.length());
        repeat(sb, pattern, n);
        return sb.toString();
    }

    public static void repeat(StringBuffer sb, char ch, int n) {
        repeat(sb, ch + "", n);
    }

    /**
     * pattern�� Ư�� Ƚ����ŭ �ݺ��ؼ� buffer�� �־��ش�. buffer�� null�̸�
     * �׳� ���ϵȴ�
     */
    public static void repeat(StringBuffer sb, String pattern, int n) {
        if (null == pattern)
            return;
        for (int i = 0; i < n; i++) {
            sb.append(pattern);
        }
    }

    // ------------------------------------------------------------------ insert

    /**
     * ������ pos ��ġ���� char�� �������ش�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.insert(1234, 3, '.');
     * // output --> "1.234"
     * </pre>
     * 
     * </blockquote>
     *
     * @see #insertRight(String, int, String)
     */
    public static String insertRight(long l, int pos, char ch) {
        return insertRight(Long.toString(l), pos, ch + "");
    }

    /**
     * ���ڿ��� Ư�� ��ġ�� Ư�� ���ڸ� ������ �ش�.
     *
     * @see #insertRight(String, int, String)
     */
    public static String insertRight(String value, int pos, char ch) {
        return insertRight(value, pos, ch + "");
    }

    /**
     * ������ Ư�� ��ġ�� Ư�� ���ڿ��� ������ �ش�.
     *
     * @see #insertRight(String, int, String)
     */
    public static String insertRight(long l, int pos, String sep) {
        return insertRight(Long.toString(l), pos, sep);
    }

    /**
     * ���ڿ��� Ư�� ��ġ�� Ư�� ���ڿ��� ������ �ش�. ���ڿ��� null�̶�� �ܼ���
     * �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.insert("020311", 2, "-");
     * // output --> "02-03-01"
     * </pre>
     * 
     * </blockquote>
     */
    public static String insertRight(String value, int pos, String sep) {
        if (null == value)
            return null;
        int len = value.length();
        StringBuffer sb = new StringBuffer(value);
        for (int i = len; i > pos; i -= pos) {
            sb.insert(i - pos, sep);
        }
        return sb.toString();
    }

    /**
     * ������ Ư�� ��ġ�� Ư�� ���ڿ��� ������ �ش�.
     *
     * @see #insertLeft(String, int, String)
     */
    public static String insertLeft(long l, int pos, String sep) {
        return insertLeft(Long.toString(l), pos, sep);
    }

    /**
     * ���ڿ��� Ư�� ��ġ�� Ư�� ���ڿ��� ������ �ش�. �տ������� �����Ѵ�.
     */
    public static String insertLeft(String value, int pos, String sep) {
        if (null == value)
            return null;
        int len = value.length();
        StringBuffer sb = new StringBuffer(len);
        int currentPos = 0;
        int nextPos = 0;
        while ((nextPos = currentPos + pos) < len) {
            sb.append(value.substring(currentPos, nextPos));
            sb.append(sep);
            currentPos = nextPos;
        }
        if (currentPos < len) {
            sb.append(value.substring(currentPos));
        }
        return sb.toString();
    }

    /**
     * ������ ���ʺ��� ������ ������ ���ʺ��� ������ �������� �����Ͽ� ������ �ش�
     *
     * @param direction LEFT�� RIGHT�� �ȴ�
     * @see #insertLeft(String, int, String)
     * @see #insertRight(String, int, String)
     */
    public static String insert(String value, int pos, String sep,
            int direction) {
        if (LEFT == direction)
            return insertLeft(value, pos, sep);
        else if (RIGHT == direction)
            return insertRight(value, pos, sep);
        return null;
    }

    // ----------------------------------------------------------------- extract

    /**
     * ���ڿ��� �յڿ��� Ư�� ���ڿ��� �߶󳽴�. ���� ���ڿ��� null�̶��
     * null�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.strip("abcdab", "ab");
     * // output --> "cd"
     * </pre>
     * 
     * </blockquote>
     */
    public static String strip(String str, String delim) {
        str = stripStart(str, delim);
        return stripEnd(str, delim);
    }

    /**
     * ���� �κ��� ��Ī�Ǵ� substring�� strip�Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.stripStart("abcdef", "abcd");
     * // output --> "ef"
     * </pre>
     * 
     * </blockquote>
     *
     * @param prefix strip�ϰ��� �ϴ� ���ڿ��̴�. null�̶�� ������ �����Ѵ�.
     *               �� {@link #ltrim(String)}�� ���� ��Ȱ�� �����Ѵ�.
     *
     * @return String string�� null�̶�� null�� �����Ѵ�. ���� string��
     *         prefix�� ���ٸ� ���ڿ��� �����Ѵ�
     */
    public static String stripStart(String str, String prefix) {
        if (str == null)
            return str;
        int start = 0;
        if (prefix == null) {
            while (Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (str.startsWith(prefix)) {
            if (str.equals(prefix)) // ���� ���ٸ� ���ڷ��� ����
                return "";
            return str.substring(prefix.length());
        }
        return str.substring(start);
    }

    /**
     * �� �κ��� ��Ī�Ǵ� substring�� strip�Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.stripEnd("abcdef", "ef");
     * // output --> "abcd"
     * </pre>
     * 
     * </blockquote>
     *
     * @param postfix strip�ϰ��� �ϴ� ���ڿ��̴�. null�̶�� ������ �����Ѵ�.
     *                �� {@link #rtrim(String)}�� ���� ��Ȱ�� �����Ѵ�.
     *
     * @return String Stirng�� null�̶�� null�� �����Ѵ�. string�� postfix��
     *         ���ٸ� ���ڿ��� �����Ѵ�
     */
    public static String stripEnd(String str, String postfix) {
        if (str == null)
            return str;
        int end = str.length();
        if (null == postfix) {
            while (Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (str.endsWith(postfix)) {
            if (str.equals(postfix)) // ���� ���ٸ� ���ڷ��� ����
                return "";
            return str.substring(0, str.lastIndexOf(postfix));
        }
        return str.substring(0, end);
    }

    /**
     * string���� Ư�� string�� ���� ���� ���� �����Ѵ�
     *
     * @param direction ������ ��Ÿ����. StringUtil.LEFT�� ��� chompLast()�̰�
     *                  StringUtil.RIGHT�� ���� chompFirst()�� �۵��Ѵ�
     *
     * @see #chompLast(String, String, int)
     * @see #chompFirst(String, String, int)
     */
    public static String chomp(String str, String sep, int max, int direction) {
        if (direction == LEFT)
            return chompLast(str, sep, max);
        else if (direction == RIGHT)
            return chompFirst(str, sep, max);
        return null;
    }

    /**
     * string���� Ư�� string�� ���� �͸� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.chompFirst("abcd\cdef\de", "\\");
     * output --> "de"
     * </pre>
     * 
     * </blockquote>
     */
    public static String chompFirst(String str, String sep) {
        return chompFirst(str, sep, -1);
    }

    /**
     * string���� Ư�� string�� ���� �͸� �����Ѵ�. string�� null�̸� null��
     * �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.chompFirst("abcd\cdef\de", "\\", 1);
     * output --> "cdef\de"
     * </pre>
     * 
     * </blockquote>
     *
     * @param max chomp�� Ƚ���� ��Ÿ����. ���� -1�̶�� ��� �����Ѵ�
     */
    public static String chompFirst(String str, String sep, int max) {
        if (null == str)
            return null;
        int index = str.indexOf(sep);
        if (index != -1) {
            if (--max != 0) { // chomp�� Ƚ���� �پ��ش�
                return chompFirst(str.substring(index + sep.length()),
                        sep, max);
            }
            return str.substring(index);
        } else {
            return str;
        }
    }

    /**
     * string���� Ư�� string�� ���� �͸� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.chompLast("abcd\cdef\de", "\\");
     * output --> "abcd"
     * </pre>
     * 
     * </blockquote>
     */
    public static String chompLast(String str, String sep) {
        return chompLast(str, sep, -1);
    }

    /**
     * string���� Ư�� string�� ���� �͸� �����Ѵ�. string�� null�̶�� null��
     * �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.chompLast("abcd\cdef\de", "\\", 1);
     * output --> "abcd\cdef"
     * </pre>
     * 
     * </blockquote>
     *
     * @param max chomp�� Ƚ���� ��Ÿ����. ���� -1�̶�� ��� �����Ѵ�
     */
    public static String chompLast(String str, String sep, int max) {
        if (null == str)
            return null;
        int index = str.lastIndexOf(sep);
        if (index != -1) {
            if (--max != 0) { // chomp�� Ƚ���� �پ��ش�
                return chompLast(str.substring(0, index), sep, max);
            }
            return str.substring(0, index);
        } else {
            return str;
        }
    }

    /**
     * String�� ������ \n�� \r\n, \r�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.v("abcd\r\n");
     * // output --> "abc"
     * </pre>
     * 
     * </blockquote>
     */
    public static String chopNewline(String str) {
        if (null == str)
            return null;
        int index = str.length() - 1;
        char last = str.charAt(index);
        if (last == '\n') {
            if (str.charAt(index - 1) == '\r') {
                index--;
            }
        } else if (last != '\r') {
            return str;
        }
        return str.substring(0, index);
    }

    /**
     * Ư�� ���ڿ� ���̿� �ִ� ���ڸ� �����Ѵ�. matching�Ǵ� ���� ���ٸ� null��
     * �����Ѵ�. string�� null�̸� null�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = StringUtil.substring("abcdef", "ab", "ef");
     * // output --> "cd"
     * </pre>
     * 
     * </blockquote>
     */
    public static String substring(String str, String open, String close) {
        if (null == str)
            return null;
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    // ------------------------------------------------------------------- index

    /**
     * �迭�� ��� ���ڿ��� ���ڿ��� �����ϰ� �ִ� ���� ���ʿ� �����ϴ� index����
     * �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * int index = StringUtil.indexOf("abcd", new String[] { "bc", "ab" });
     * // output --> -1
     * </pre>
     * 
     * </blockquote>
     *
     * @return �迭�� index ���� �����Ѵ�. string�� <code>null</code>�̸�
     *         �̰ų� �߰ߵ��� ���� ������ <code>-1</code>�� �����Ѵ�.
     * @exception java.lang.NullPointerExcetion ���� <code>strs</code>��
     *                                          null�̶�� �߻��Ѵ�.
     */
    public static int indexOf(String str, String[] strs) {
        if (null == str)
            return -1;
        int len = strs.length;
        int tmp = 0;
        int ret = Integer.MAX_VALUE;

        for (int i = 0; i < len; i++) {
            tmp = str.indexOf(strs[i]);
            if (tmp == -1) {
                continue;
            }
            if (tmp < ret) {
                ret = tmp;
                break;
            }
        }
        return (ret == Integer.MAX_VALUE ? -1 : ret);
    }

    /**
     * �迭�� ��� ���ڿ��� string�� �Ϻκ� ��ġ�� ���� ���ʿ� �ִ� ���� ã�´�.
     * ���� �߰ߵ��� ������ -1�� �����Ѵ�. string�� null�̸� -1�� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * int index = StringUtil.lastIndexOf("abcd", new String[] { "ab", "bc" });
     * // output --> 1;
     * </pre>
     * 
     * </blockquote>
     */
    public static int lastIndexOf(String str, String[] strs) {
        if (null == str)
            return -1;
        int len = strs.length;
        int ret = -1;
        int tmp = 0;
        for (int i = 0; i < len; i++) {
            tmp = str.lastIndexOf(strs[i]);
            if (tmp > ret) {
                ret = tmp;
            }
        }
        return ret;
    }

    /**
     * �󸶳� sub string�� matching�Ǵ����� �����Ѵ�. string�� null�̸� 0�� ����
     * �Ѵ�
     */
    public static int countMatches(String str, String sub) {
        if (null == str)
            return 0;
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }

    // -------------------------------------------------------------------- wrap

    /**
     * �� �ϳ��� string line�� ���� ���� ��ŭ wrap�ϱ� ���� �޼ҵ��̴�. ���̸�
     * �Ѿ�� ���� newline�� ������ �ش�. ���������� ����ϱ� ����
     * �޼ҵ��̴�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = wrapLine("Java is Beautiful.", "|", 4);
     * // output --> "Java| is |Beau|tifu|l."
     * </pre>
     * 
     * </blockquote>
     *
     * @param line       wrap�ϱ� ���� string�� ��Ÿ����
     * @param newline    line�� �����ϱ� ���� ���Ǵ� newline���� ��Ÿ����.
     * @param wrapColumn wrap�ϱ� ���� column�� ���̸� ��Ÿ����.
     */
    protected static String wrapLine(String line, String newline,
            int wrapColumn) {
        StringBuffer sb = new StringBuffer(line.length());
        int currentPos = 0;
        int nextPos = wrapColumn;
        while (line.length() > nextPos) {
            sb.append(line.substring(currentPos, nextPos));
            sb.append(newline);
            currentPos = nextPos;
            nextPos += wrapColumn;
        }
        if (currentPos < line.length()) {
            sb.append(line.substring(currentPos));
        }
        return sb.toString();
    }

    /**
     * �� �ϳ��� string line�� ���� ���� ��ŭ wrap�ϱ� ���� �޼ҵ��̴�. ���̸�
     * �Ѿ�� ���� newline�� ������ �ش�. ���� �ܾ �и��Ǹ� split�� ������
     * �ش�.
     *
     * <blockquote>
     * 
     * <pre>
     * String str = wrapWordLine("Java is Beautiful.", "\n", "-", 4);
     * // output --> "Java\n is-\n Be-\naut-\nifu-\nl."
     * </pre>
     * 
     * </blockquote>
     *
     * @param line       wrap�ϱ� ���� string�� ��Ÿ����
     * @param newline    line�� �����ϱ� ���� ���Ǵ� newline���� ��Ÿ����.
     * @param split      �ܾ �����ϱ� ���� ���ȴ�
     * @param wrapColumn wrap�ϱ� ���� column�� ���̸� ��Ÿ����.
     */
    protected static String wrapWordLine(String line, String newline,
            String split, int wrapColumn) {
        int len = line.length();
        StringBuffer sb = new StringBuffer(len);
        String str = null;
        for (int i = 0; i < len; i += wrapColumn) {
            if (i >= len - wrapColumn) {
                sb.append(line.substring(i));
                break;
            }
            str = line.substring(i, i + wrapColumn);
            // �����̶�� �ܾ �и�
            if (Character.isWhitespace(line.charAt(i + wrapColumn))) {
                sb.append(str);
            } else { // ������ �ƴ϶� ���ڶ�� �ܾ��̴�
                // ���� ���� ���ڰ� �����̶�� �ܾ �и��� ���̴�
                if (Character.isWhitespace(str.charAt(wrapColumn - 2))) {
                    // ���� ���ڰ� �����̶��
                    if (Character.isWhitespace(str.charAt(wrapColumn - 1))) {
                        sb.append(str);
                    } else {
                        sb.append(str.substring(0, wrapColumn - 2));
                        i -= 2;
                    }
                    // sb.append(str);
                } else {
                    sb.append(str.substring(0, wrapColumn - 1));
                    sb.append(split);
                    --i; // �ڷ� �̵�
                }
            }
            sb.append(newline);
        }
        return sb.toString();
    }

    // --------------------------------------------------------------------- set

    /**
     * Ư�� ���ڸ� �ٸ� ���ڷ� �ٲ۴�. target�� null�̸� null�� �����ϰ� rep��
     * with�� null�̸� ���� ���ڿ��� �����Ѵ�.
     *
     * <blockquote>
     * 
     * <pre>
     * translate("hello", "ho", "jy") => jelly
     * </pre>
     * 
     * </blockquote>
     */
    public static String translate(String target, String rep, String with) {
        if (null == target)
            return null;
        StringBuffer sb = new StringBuffer(target.length());
        char[] chars = target.toCharArray();
        char[] withChars = with.toCharArray();
        int len = chars.length;
        int withMax = with.length() - 1;
        for (int i = 0; i < len; i++) {
            int index = rep.indexOf(chars[i]);
            if (index != -1) {
                if (index > withMax) {
                    index = withMax;
                }
                sb.append(withChars[index]);
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    // -------------------------------------------------------------- formatting

}