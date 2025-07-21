package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom2.Element;

/**
 * TrustForm에서 받은 파라미터 데이터를 적절한 객체 타입으로 변환
 * 
 * 프로그램명: ParameterConverter.java
 * 설명: 파라미터 데이터의 타입 변환을 담당하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 문자열 데이터를 적절한 Java 타입으로 변환
 * - Map과 List 형태의 데이터 변환
 * - 기본값 처리
 * 
 * @author
 * @version 1.0
 * @see
 * 
 */
public class ParameterConverter {

    /**
     * 파라미터 맵을 입력받아 적절한 객체 타입으로 변환된 맵을 반환한다.
     * 
     * @param componentId 컴포넌트 ID
     * @param interfaceId 인터페이스 ID
     * @param map         파라미터 맵
     * @return 변환된 맵
     */
    public Map getSettingMap(String componentId, String interfaceId, Map map) {

        Map resultMap = new HashMap(); /* 결과 맵 */

        Iterator iter = map.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            resultMap.put(key, getTypedData(componentId, interfaceId, key, value));
        }

        return resultMap;
    }

    /**
     * 파라미터 리스트를 입력받아 적절한 객체 타입으로 변환된 리스트를 반환한다.
     * 
     * @param componentId 컴포넌트 ID
     * @param interfaceId 인터페이스 ID
     * @param list        파라미터 리스트
     * @return 변환된 리스트
     */
    public List getSettingList(String componentId, String interfaceId, List list) {

        List resultList = new ArrayList(); /* 결과 리스트 */

        for (int i = 0; i < list.size(); i++) {

            Map resultMap = new HashMap();

            Map map = (Map) list.get(i);

            Iterator iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                resultMap.put(key, getTypedData(componentId, interfaceId, key, value));
            }

            resultList.add(resultMap);
        }

        return resultList;

    }

    /**
     * 파라미터의 해당 노드에 입력받아 타입에 맞는 데이터 타입을 반환한다.
     * 
     * @param componentId   컴포넌트 ID
     * @param interfaceId   인터페이스 ID
     * @param nodeName      노드 이름
     * @param childNodeName 자식 노드 이름
     * @return 데이터 타입 문자열
     */
    public String getDataType(String componentId, String interfaceId, String nodeName, String childNodeName) {

        /*
         * root 엘리먼트
         */
        Element rootElement = ParameterInfo.getInstance().getDoc(componentId).getRootElement();

        /*
         * 해당 객체 엘리먼트
         */
        Element element = rootElement.getChild(interfaceId);

        /*
         * 해당 필드 엘리먼트
         */
        Element e = element.getChild(nodeName);

        /*
         * 해당 필드의 타입 반환
         */
        return e.getAttribute("type").getValue();

    }

    /**
     * 데이터 타입에 맞는 데이터를 변환한다.
     * 
     * @param componentId 컴포넌트 ID
     * @param interfaceId 인터페이스 ID
     * @param nodeName    노드 이름
     * @param nodeValue   노드 값
     * @return 변환된 객체
     */
    public Object getTypedData(String componentId, String interfaceId, String nodeName, String nodeValue) {

        /*
         * root 엘리먼트
         */
        System.out.println("ParameterConverter Test getType Data!!!");
        System.out.println("componentId = " + componentId + ",  interfaceId = " + interfaceId + ", nodeName = "
                + nodeName + ", nodeValue = " + nodeValue);
        Element rootElement = ParameterInfo.getInstance().getDoc(componentId).getRootElement();

        /*
         * 해당 객체 엘리먼트
         */
        Element element = rootElement.getChild(interfaceId);

        /*
         * 해당 필드 엘리먼트
         */
        Element e = element.getChild(nodeName);

        /*
         * 해당 필드의 타입에 따라 데이터 변환
         */
        if (e != null && e.getAttribute("type") != null) {

            if (e.getAttribute("type").getValue().equals("Integer")) {

                return (Object) (new Integer(getDefaultValueData(e, nodeValue)));

            } else if (e.getAttribute("type").getValue().equals("Long")) {

                return (Object) (new Long(getDefaultValueData(e, nodeValue)));

            } else if (e.getAttribute("type").getValue().equals("Float")) {

                return (Object) (new Float(getDefaultValueData(e, nodeValue)));

            } else if (e.getAttribute("type").getValue().equals("Double")) {

                return (Object) (new Double(getDefaultValueData(e, nodeValue)));

            } else if (e.getAttribute("type").getValue().equals("BigDecimal")) {

                return (Object) (new BigDecimal(getDefaultValueData(e, nodeValue)));

            } else if (e.getAttribute("type").getValue().equals("Date")) {

                return (Object) (Date.valueOf(getDefaultValueData(e, nodeValue)));

            } else {

                return (Object) (new String(getDefaultValueData(e, nodeValue)));
            }
        }

        return "";

    }

    /**
     * 엘리먼트의 기본값과 노드값을 비교하여 데이터 반환
     * 
     * @param element   엘리먼트
     * @param nodeValue 노드 값
     * @return 처리된 문자열 값
     */
    private String getDefaultValueData(Element element, String nodeValue) {

        if (nodeValue == null || nodeValue.equals("")) {
            return element.getAttribute("default").getValue();
        } else {
            return nodeValue;
        }
    }

}
