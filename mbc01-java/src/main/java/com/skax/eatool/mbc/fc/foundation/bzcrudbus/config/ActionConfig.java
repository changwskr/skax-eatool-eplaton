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

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * 액션 설정 관리 클래스
 * 
 * 프로그램명: ActionConfig.java
 * 설명: 비즈니스 코드별 액션 설정을 로드하고 관리하는 클래스
 * 작성일: 2008-07-22
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 비즈니스 코드별 액션 설정 파일 로드
 * - 명령어(Command) 정보 관리
 * - 엘리먼트 맵 관리
 * - 싱글톤 패턴으로 인스턴스 관리
 * 
 * @author ywahn
 *
 *         Action 설정 Class
 */
public class ActionConfig {

	/** 설정 파일 배열 */
	private static final String[] CONFIG_FILE = {};

	/** 액션 맵 */
	private HashMap actionMap = null;
	/** 엘리먼트 맵 */
	private HashMap elementMap = null;

	/** 싱글톤 인스턴스 */
	private static ActionConfig actionConfig = null;

	/** 비즈니스 코드별 설정 맵 */
	private static HashMap<String, ActionConfig> configMap = new HashMap<String, ActionConfig>();

	/**
	 * 기본 생성자
	 * 
	 * @param configFile 설정 파일 배열
	 */
	private ActionConfig(String[] configFile) {

		try {
			actionMap = new HashMap();
			elementMap = new HashMap();

			for (int a = 0; a < configFile.length; a++) {

				InputStream inputStream = ParameterInfo.class.getResourceAsStream(configFile[a]);
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(inputStream);

				Collection col = doc.getRootElement().getChildren("command");
				Iterator i = col.iterator();
				while (i.hasNext()) {
					Element cmdEle = (Element) i.next();
					String id = cmdEle.getAttributeValue("id");
					String type = cmdEle.getAttributeValue("type");

					Collection col2 = cmdEle.getChildren("service");
					Iterator j = col2.iterator();
					ArrayList actionList = new ArrayList();
					while (j.hasNext()) {
						Element svcEle = (Element) j.next();
						String key = svcEle.getAttributeValue("key");
						String name = svcEle.getAttributeValue("name");
						String server = svcEle.getAttributeValue("server");
						String element = svcEle.getAttributeValue("element");
						String method = svcEle.getAttributeValue("method");
						String seq = svcEle.getAttributeValue("seq");
						String sqlId = svcEle.getAttributeValue("sql_id");

						elementMap.put(id + "." + key, element);

						CommandObj cmdObj = new CommandObj(type, key, name, server, element, method, seq, sqlId);
						actionList.add(cmdObj);
					}

					if (actionMap.containsKey(id)) {
						System.out.println("***********************************************************");
						System.out.println("** EXIST ID --   File Name :" + CONFIG_FILE[a] + ",    ID :" + id);
						System.out.println("***********************************************************");
					}
					actionMap.put(id, actionList);
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
	 * @return ActionConfig 인스턴스
	 */
	public static synchronized ActionConfig getInstance(String bizCd) {
		String bizCode = bizCd.toUpperCase();

		if (!configMap.containsKey(bizCode)) {
			String[] configFile = new String[1];
			configFile[0] = IFRSConfig.getInstance().getInfo("ConfigFile", bizCode);
			configMap.put(bizCode, new ActionConfig(configFile));
		}

		return (ActionConfig) configMap.get(bizCode);
	}

	/**
	 * 기본 인스턴스를 반환한다.
	 * 
	 * @return ActionConfig 인스턴스
	 */
	public static synchronized ActionConfig getInstance() {

		if (actionConfig == null) {
			actionConfig = new ActionConfig(CONFIG_FILE);
		}

		return actionConfig;
	}

	/**
	 * Command List Return
	 * 
	 * @param id 명령어 ID
	 * @return 명령어 리스트
	 */
	public ArrayList getCmdList(String id) {
		return (ArrayList) actionMap.get(id);
	}

	/**
	 * 엘리먼트 맵을 반환한다.
	 * 
	 * @return 엘리먼트 맵
	 */
	public HashMap getElementMap() {
		return elementMap;
	}

	/**
	 * 액션 맵이 비어있는지 확인한다.
	 * 
	 * @return 비어있으면 true, 아니면 false
	 */
	public boolean isEmpty() {
		return actionMap.isEmpty();
	}

}
