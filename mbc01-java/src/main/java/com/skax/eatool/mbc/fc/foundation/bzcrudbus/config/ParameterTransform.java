package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 파라미터를 필요에 따라 입력받는 처리를 담당한다.
 * 
 * 프로그램명: ParameterTransform.java
 * 설명: HTTP 요청 파라미터를 변환하고 처리하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - HTTP 요청 파라미터 변환
 * - 그리드 데이터 처리
 * - 파라미터 타입 변환
 * - CRUD 작업별 데이터 분리
 * 
 * @author
 * @version 1.0
 * @see
 */
public class ParameterTransform {

    /** 파라미터 변환기 */
    private ParameterConverter parameterConverter = null;

    /** 로거 */
    private Logger logger = Logger.getLogger(ParameterTransform.class);

    /**
     * 기본 생성자
     */
    public ParameterTransform() {
        parameterConverter = new ParameterConverter();
    }

    /**
     * 파라미터를 그리드 형태의 데이터로 변환한다.
     * 
     * @param componentId     컴포넌트 ID
     * @param interfaceId     인터페이스 ID
     * @param parameterString 파라미터 문자열
     * @param insertList      삽입 리스트
     * @param updateList      수정 리스트
     * @param deleteList      삭제 리스트
     */
    public void setParameterToGridData(String componentId, String interfaceId, String parameterString, List insertList,
            List updateList, List deleteList) {

        /*
         * row 리스트 분리
         */
        String[] rowStringList = parameterString.split("\\|");

        for (int i = 0; i < rowStringList.length; i++) {
            System.out.println("rowStringList[" + i + "]" + rowStringList[i]);
        }

        /*
         * 헤더 정보 분리
         */
        String header = rowStringList[0];
        String[] headerList = header.split("\\^");

        /*
         * row 처리
         */
        for (int i = 1; i < rowStringList.length; i++) {

            String rowString = rowStringList[i];

            /*
             * column 리스트 분리
             */
            String[] columnStringList = rowString.split("\\^");

            /*
             * 구분자 분리(i:insert, u:update, d:delete)
             */
            String gubun = columnStringList[0];

            Map insertMap = new HashMap();
            Map updateMap = new HashMap();
            Map deleteMap = new HashMap();

            /*
             * column 처리
             */
            for (int j = 1; j < columnStringList.length; j++) {

                String columnString = columnStringList[j]; // 컬럼값

                if (gubun.equals("i")) {// insert 구분 처리
                    insertMap.put(headerList[j], getTypedData(componentId, interfaceId, headerList[j], columnString));
                    if (logger.isDebugEnabled()) {
                        logger.debug("insertMap : " + insertMap);
                    }

                } else if (gubun.equals("u")) {// update 구분 처리
                    updateMap.put(headerList[j], getTypedData(componentId, interfaceId, headerList[j], columnString));
                    if (logger.isDebugEnabled()) {
                        logger.debug("updateMap : " + updateMap);
                    }

                } else if (gubun.equals("d")) {// delete 구분 처리
                    deleteMap.put(headerList[j], getTypedData(componentId, interfaceId, headerList[j], columnString));
                    if (logger.isDebugEnabled()) {
                        logger.debug("deleteMap : " + deleteMap);
                    }
                }
            }

            /*
             * 삽입 구분이 있으면 삽입 구분 리스트에 추가
             */
            if (insertMap.size() > 0) {

                insertList.add(insertMap);
            }

            /*
             * 수정 구분이 있으면 수정 구분 리스트에 추가
             */
            if (updateMap.size() > 0) {
                updateList.add(updateMap);
            }

            /*
             * 삭제 구분이 있으면 삭제 구분 리스트에 추가
             */
            if (deleteMap.size() > 0) {
                deleteList.add(deleteMap);
            }
        }
    }

    /**
     * 파라미터를 코드별로 변환해서 리스트로 변환
     * 
     * @param componentId 컴포넌트 ID
     * @param interfaceId 인터페이스 ID
     * @param request     HTTP 요청 객체
     * @return 변환된 리스트
     */
    public List getParameterList(String componentId, String interfaceId, HttpServletRequest request) {

        List resultList = new ArrayList();

        Enumeration enumv = request.getParameterNames();

        Map map = new HashMap();
        while (enumv.hasMoreElements()) {
            String parameterName = (String) enumv.nextElement();
            map.put(parameterName,
                    getTypedData(componentId, interfaceId, parameterName, request.getParameter(parameterName)));
        }

        if (logger.isDebugEnabled()) {
            logger.debug(" getParameterList map :" + map);
        }

        resultList.add(map);

        return resultList;
    }

    /**
     * 파라미터를 코드별로 변환해서 맵으로 변환(단일건과 다중건 변환)
     * 
     * @param componentId 컴포넌트 ID
     * @param interfaceId 인터페이스 ID
     * @param request     HTTP 요청 객체
     * @return 변환된 맵
     */
    public Map getParameterMap(String componentId, String interfaceId, HttpServletRequest request) {

        Map resultMap = new HashMap();

        Enumeration enumv = request.getParameterNames();
        while (enumv.hasMoreElements()) {
            String parameterName = (String) enumv.nextElement();
            if (logger.isDebugEnabled()) {
                logger.debug("parameterName : " + parameterName);
            }
            resultMap.put(parameterName,
                    getTypedData(componentId, interfaceId, parameterName, request.getParameter(parameterName)));
        }

        return resultMap;
    }

    /**
     * 객체로 변환된 데이터를 반환
     * 
     * @param componentId 컴포넌트 ID
     * @param interfaceId 인터페이스 ID
     * @param dataName    데이터 이름
     * @param dataValue   데이터 값
     * @return 변환된 객체
     */
    private Object getTypedData(String componentId, String interfaceId, String dataName, String dataValue) {
        if (componentId == null || interfaceId == null) {
            return dataValue;
        } else {
            return parameterConverter.getTypedData(componentId, interfaceId, dataName, dataValue);
        }
    }

    /**
     * 그리드 파라미터를 변환해서 객체 형태 String타입으로 된 리스트로 변환
     * 
     * @param parameterString 파라미터 문자열
     * @return 변환된 리스트
     */
    public List getParamGridList(String parameterString) {

        List resultList = new ArrayList();

        /*
         * row 리스트 분리
         */
        String[] rowStringList = parameterString.split("\\|");

        for (int i = 0; i < rowStringList.length; i++) {
            System.out.println("rowStringList[" + i + "]" + rowStringList[i]);
        }

        /*
         * 헤더 정보 분리
         */
        String header = rowStringList[0];
        String[] headerList = header.split("\\^");

        /*
         * row 처리
         */
        for (int i = 1; i < rowStringList.length; i++) {

            Map map = new HashMap();

            String rowString = rowStringList[i];

            /*
             * column 리스트 분리
             */
            String[] columnStringList = rowString.split("\\^");

            int size = columnStringList.length;
            String columnString = "";

            /*
             * column 처리
             */
            for (int j = 0; j < headerList.length; j++) {
                if (size > j) {

                    columnString = columnStringList[j]; // 컬럼값
                } else {
                    columnString = "";
                }
                map.put(headerList[j], columnString);
            }

            /*
             * 삽입 구분이 있으면 삽입 구분 리스트에 추가
             */
            if (map.size() > 0) {
                resultList.add(map);
            }

            if (logger.isDebugEnabled()) {
                logger.debug(" getParamGridList map :" + map);
            }
        }

        return resultList;
    }

    /**
     * 파라미터를 코드별로 변환해서 맵으로 변환(단일건과 다중건 변환)
     * 
     * @param request HTTP 요청 객체
     * @return 변환된 맵
     */
    public Map getParameterMap(HttpServletRequest request) {

        Map resultMap = new HashMap();

        Enumeration enumv = request.getParameterNames();
        while (enumv.hasMoreElements()) {
            String parameterName = (String) enumv.nextElement();
            resultMap.put(parameterName, (String) request.getParameter(parameterName));
        }
        resultMap.remove("gridData");
        return resultMap;
    }

}
