package com.skax.eatool.mbc.fc.foundation.bzcrudbus.config;

/*
 * 작성자 및 날짜: 2008-07-22
 *
 * ActionConfig 설정 정보를 관리하고 저장한다.
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 클래스패스 로더 설정 관리 클래스
 * 
 * 프로그램명: LoaderConfig.java
 * 설명: 비즈니스 코드별 클래스패스 설정을 로드하고 관리하는 클래스
 * 작성일: 2008-07-22
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 비즈니스 코드별 설정 파일 로드
 * - 클래스패스 정보 관리
 * - 싱글톤 패턴으로 인스턴스 관리
 * 
 * @author ywahn
 *
 *         ClassPath 로더 Class
 */
public class LoaderConfig {

	/** 설정 파일 배열 */
	private static final String[] CONFIG_FILE = {};

	/** 클래스패스 맵 */
	private HashMap classpathMap = null;

	/** 싱글톤 인스턴스 */
	private static LoaderConfig loaderConfig = null;

	/** 비즈니스 코드별 설정 맵 */
	private static HashMap<String, LoaderConfig> configMap = new HashMap<String, LoaderConfig>();

	/**
	 * 기본 생성자
	 * 
	 * @param configFile 설정 파일 배열
	 */
	private LoaderConfig(String[] configFile) {

		try {
			classpathMap = new HashMap();

			for (int a = 0; a < configFile.length; a++) {

				InputStream inputStream = ParameterInfo.class.getResourceAsStream(configFile[a]);
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(inputStream);

				Collection col = doc.getRootElement().getChildren("class-path");
				Iterator i = col.iterator();
				while (i.hasNext()) {
					Element pathEle = (Element) i.next();
					String name = pathEle.getAttributeValue("name");

					Collection col2 = pathEle.getChildren("source");
					Iterator j = col2.iterator();
					ArrayList locationList = new ArrayList();

					while (j.hasNext()) {
						Element svcEle = (Element) j.next();
						String location = svcEle.getAttributeValue("location");

						locationList.add(location);
					}

					if (classpathMap.containsKey(name)) {
						System.out.println("***********************************************************");
						System.out.println("** Exist Name --   File Name :" + CONFIG_FILE[a] + ",    Name : " + name);
						System.out.println("***********************************************************");
					}
					classpathMap.put(name, locationList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 비즈니스 코드별 인스턴스를 반환한다.
	 * 
	 * @param bizCd 비즈니스 코드
	 * @return LoaderConfig 인스턴스
	 */
	public static LoaderConfig getInstance(String bizCd) {
		String bizCode = bizCd.toUpperCase();

		if (!configMap.containsKey(bizCode)) {
			String[] configFile = new String[1];
			configFile[0] = IFRSConfig.getInstance().getInfo("ConfigFile", bizCode);
			configMap.put(bizCode, new LoaderConfig(configFile));
		}

		return (LoaderConfig) configMap.get(bizCode);
	}

	/**
	 * 기본 인스턴스를 반환한다.
	 * 
	 * @return LoaderConfig 인스턴스
	 */
	public static LoaderConfig getInstance() {

		String bizCode = "LDR";

		if (!configMap.containsKey(bizCode)) {
			String[] configFile = new String[1];
			configFile[0] = IFRSConfig.getInstance().getInfo("ConfigFile", bizCode);
			configMap.put(bizCode, new LoaderConfig(configFile));
		}

		return (LoaderConfig) configMap.get(bizCode);
	}

	/**
	 * ClassPath List Return
	 * 
	 * @param name 클래스패스 이름
	 * @return 클래스패스 리스트
	 */
	public ArrayList getClassPathList(String name) {
		return (ArrayList) classpathMap.get(name);
	}

	/**
	 * 클래스패스 맵이 비어있는지 확인한다.
	 * 
	 * @return 비어있으면 true, 아니면 false
	 */
	public boolean isEmpty() {
		return classpathMap.isEmpty();
	}

}
