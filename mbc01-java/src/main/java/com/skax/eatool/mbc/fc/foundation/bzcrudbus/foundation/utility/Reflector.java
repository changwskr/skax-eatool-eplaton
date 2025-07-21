package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.lang.reflect.*;
import java.util.*;

/**
 * Reflection을 사용하기 위한 utility class이다.
 * 특정 object의 attribute(상위 class의 attribute까지 포함한다)를 모두 map으로 바꾼다.<br>
 * 이 때 map의 key는 attribute의 이름이고 value는 attribute의 값이다.<br>
 * 
 * 프로그램명: Reflector.java
 * 설명: Java Reflection을 사용하여 객체의 필드 정보를 조작하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 객체를 Map으로 변환
 * - 객체를 String으로 변환
 * - 필드 정보 추출
 * - Reflection 기반 객체 조작
 */
final public class Reflector {
  /**
   * 기본 생성자
   */
  private Reflector() {
    super();
  }

  /**
   * 클래스의 모든 필드를 반환한다.
   * 
   * @param clazz 클래스
   * @return 필드 리스트
   */
  private static List allFileds(Class clazz) {
    List fields = new ArrayList();
    Class parent = clazz;

    do {
      fields.addAll(Arrays.asList(parent.getDeclaredFields()));
      parent = parent.getSuperclass();
    } while (!parent.equals(Object.class));

    return fields;
  }

  /**
   * 특정 object의 attribute(상위 class의 attribute까지 포함한다)를 모두 map으로 바꾼다.<br>
   * 이 때 map의 key는 attribute의 이름이고 value는 attribute의 값이다.<br>
   * 
   * @param o 변환할 객체
   * @return 필드 정보가 담긴 Map
   */
  public static Map objectToMap(Object o) {
    Map result = new HashMap();

    List fields = Reflector.allFileds(o.getClass());

    // synchronized (o)
    // {
    try {
      for (Iterator i = fields.iterator(); i.hasNext();) {
        Field f = (Field) i.next();
        f.setAccessible(true);
        result.put(f.getName(), f.get(o));
        f.setAccessible(false);
      }
    } catch (IllegalAccessException _e) {
    }
    // }

    return result;
  }

  /**
   * 특정 object의 attribute(상위 class의 attribute까지 포함한다)를 모두 String 문자열로 바꾼다.<br>
   * 문자열은 다음과 같은 형식이다.<br>
   * {class name=(attribute1 name : attribute1 value)(attribute2 name : attribute2
   * value)...}<br>
   * 
   * @param o 변환할 객체
   * @return 필드 정보가 담긴 String
   */
  public static String objectToString(Object o) {
    StringBuffer result = new StringBuffer();
    Class clazz = o.getClass();

    result.append("{").append(clazz.getName()).append("=");

    List fields = Reflector.allFileds(clazz);

    // synchronized (o)
    // {
    try {
      for (Iterator i = fields.iterator(); i.hasNext();) {
        Field f = (Field) i.next();
        f.setAccessible(true);
        result.append("(").append(f.getName()).append(":").append(f.get(o)).append(")");
        f.setAccessible(false);
      }
    } catch (IllegalAccessException _e) {
    }
    // }

    result.append("}");

    return result.toString();
  }
}
