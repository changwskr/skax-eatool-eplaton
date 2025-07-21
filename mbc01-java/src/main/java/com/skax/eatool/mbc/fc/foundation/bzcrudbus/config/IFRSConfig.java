package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * IFRS 설정 파일을 읽어서 해당 설정 정보를 Hashtable에 저장하고 관리한다.
 * 
 * 프로그램명: IFRSConfig.java
 * 설명: IFRS 프레임워크의 설정 파일을 로드하고 관리하는 싱글톤 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - XML 설정 파일 로드
 * - 시스템 정보 관리
 * - 설정값 조회
 * - 서버 정보 파싱
 * 
 * @author : jws
 * @version : 1.0
 */

public class IFRSConfig {
    /**
     * 설정 파일명을 설정 파일 경로와 클래스 패스에 반드시 존재해야 한다.
     */
    private String configFileName = null;

    // private static Log log = LogFactory.getLog(IFRSConfig.class);

    /** 설정 정보를 저장하는 Hashtable */
    private static Hashtable interfaces = new Hashtable();

    /** 싱글톤 인스턴스 */
    private static IFRSConfig config;

    /**
     * 기본 생성자
     * 
     * @throws Exception 초기화 중 오류 발생 시
     */
    private IFRSConfig() throws Exception {
        init();
    }

    /**
     * IFRSConfig 클래스의 Singletone 인스턴스를 반환한다.
     * 
     * @return IFRSConfig 인스턴스
     */
    public static IFRSConfig getInstance() {
        if (config == null) {
            try {
                config = new IFRSConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    /**
     * 설정 파일을 다시 로드한다.
     */
    public void reLoadConfig() {

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * XML로 정의된 설정 파일을 읽어서 Hashtable에 저장한다.
     * 
     * @throws Exception 설정 파일 로드 중 오류 발생 시
     */
    private void init() throws Exception {

        // XMSConfig 초기화
        // IfrsLogHelper.getCom().infoF("[IFRSConfig] init");

        java.io.InputStream is = null;

        try {

            if (configFileName == null) {
                configFileName = Env.getValue("config.default.file");
            }

            // IfrsLogHelper.getCom().infoF("[IFRSConfig] init : Load Config File : " +
            // configFileName);

            is = IFRSConfig.class.getResourceAsStream(configFileName);

            SAXBuilder builder = new SAXBuilder(false);

            Document doc = builder.build(is);

            // 2 config 파일의 루트 요소를 가져온다.
            Element root = doc.getRootElement();

            readSysConfig(root);

        } catch (JDOMException jdome) {

            jdome.printStackTrace();

            throw new Exception(jdome.getMessage());
        } catch (Exception e) {

            e.printStackTrace();

            throw new Exception(e.getMessage());
        }

    }

    /**
     * SystemInfo를 읽는다.
     * 
     * @param element 루트 엘리먼트
     */
    private static void readSysConfig(Element element) {
        // create serviceInfo

        Iterator iterator = element.getChildren("SystemInfo").iterator();
        Element temp = null;
        while (iterator.hasNext()) {
            temp = (Element) iterator.next();
            // IfrsLogHelper.getCom().infoF("[IFRSConfig] Reading SystemInfo: " +
            // temp.getAttributeValue("id"));
            interfaces.put(temp.getAttributeValue("id"), readAttributes(temp));
        }

    }

    /**
     * XML 엘리먼트의 속성을 읽는다.
     * 
     * @param element 엘리먼트
     * @return ConfigInfo 객체
     */
    private static ConfigInfo readAttributes(Element element) {

        ConfigInfo serviceInfo = new ConfigInfo();
        Iterator iterator = element.getChildren().iterator();
        serviceInfo.setSysConfig(makeSubSet(iterator));
        return serviceInfo;

    }

    /**
     * 하위 엘리먼트들을 HashMap으로 변환한다.
     * 
     * @param iterator 엘리먼트 반복자
     * @return HashMap
     */
    private static HashMap makeSubSet(Iterator iterator) {
        HashMap subMap = new HashMap();
        Element temp = null;
        while (iterator.hasNext()) {
            temp = (Element) iterator.next();
            subMap.put(temp.getName(), temp.getText());
        }

        return subMap;
    }

    /**
     * 주어진 시스템 정보에서 설정 정보를 검색한다.
     * 
     * @param sysInfo  시스템 정보 키
     * @param confName 설정 이름
     * @return 설정값
     */
    public String getInfo(String sysInfo, String confName) {
        ConfigInfo info = (ConfigInfo) interfaces.get(sysInfo);
        // IfrsLogHelper.getCom().infoF("[IFRSConfig] getinfo[" + info + "]");
        return (String) info.getSysConfig().get(confName);
    }

    /**
     * 서버 URL에서 IP 주소를 추출한다.
     * 
     * @param sysInfo  시스템 정보 키
     * @param confName 설정 이름
     * @return IP 주소
     */
    public String getServerIpAddress(String sysInfo, String confName) {
        String serverUrl = getInfo(sysInfo, confName);
        int delimeter = serverUrl.lastIndexOf(":");
        String ip = serverUrl.substring(0, delimeter);
        return ip;
    }

    /**
     * 서버 URL에서 포트 번호를 추출한다.
     * 
     * @param sysInfo  시스템 정보 키
     * @param confName 설정 이름
     * @return 포트 번호
     */
    public String getServerPort(String sysInfo, String confName) {
        String serverUrl = getInfo(sysInfo, confName);
        int delimeter = serverUrl.lastIndexOf(":");
        String port = serverUrl.substring(delimeter + 1, serverUrl.length());
        return port;
    }

    /**
     * 메인 메서드 (테스트용)
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        // TODO 코드 테스트용 메서드 구현

        String conv = IFRSConfig.getInstance().getInfo("ReportServerInfo", "IP");

    }

}
