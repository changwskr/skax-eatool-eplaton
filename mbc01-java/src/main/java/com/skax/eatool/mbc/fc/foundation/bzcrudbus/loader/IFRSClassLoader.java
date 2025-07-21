package com.skax.eatool.mbc.fc.foundation.bzcrudbus.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.LoaderConfig;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Utils;

/**
 * IFRS 클래스 로더 클래스
 * 
 * 프로그램명: IFRSClassLoader.java
 * 설명: IFRS 관련 동적 클래스 로딩을 관리하는 싱글톤 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IFRS 관련 동적 클래스 로딩
 * - 클래스패스 관리
 * - 싱글톤 패턴 구현
 * - 클래스 인스턴스 생성
 * - 프록시 인스턴스 생성
 * 
 * @version 1.0
 */
public class IFRSClassLoader {

	/** 동적 로더 인스턴스 */
	DynamicLoader loader = null;

	/** 싱글톤 인스턴스 */
	private static IFRSClassLoader instance;

	/** 리셋 플래그 */
	private static boolean reset = true;

	/**
	 * 기본 생성자
	 * 설정에서 IFRS 클래스패스를 읽어와서 동적 로더를 초기화한다.
	 */
	private IFRSClassLoader() {

		// New ClassPath Include
		ArrayList arrList = LoaderConfig.getInstance().getClassPathList("IFRS");
		String location = null;
		loader = new DynamicLoader();

		for (Iterator itr = Utils.snapshotIterator(arrList); itr.hasNext();) {

			location = (String) itr.next();

			if (location != null && !location.trim().equals("")) {
				loader.addSourceDir(new File(location));
			}
		}
	}

	/**
	 * 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return IFRSClassLoader 인스턴스
	 */
	public static synchronized IFRSClassLoader getInstance() {
		if (reset || instance == null) {
			instance = new IFRSClassLoader();
			reset = false;
		}
		return instance;
	}

	/**
	 * 인스턴스를 리셋한다.
	 */
	public static void reset() {
		reset = true;
	}

	/**
	 * 클래스 이름으로 인스턴스를 생성한다.
	 * 
	 * @param className 클래스 이름
	 * @return 생성된 인스턴스
	 */
	public Object getClassInstance(String className) {
		return loader.newInstance(className);
	}

	/**
	 * 인터페이스와 구현 클래스 이름으로 프록시 인스턴스를 생성한다.
	 * 
	 * @param interfaceClass 인터페이스 클래스
	 * @param implClassName  구현 클래스 이름
	 * @return 생성된 프록시 인스턴스
	 */
	public Object getClassInstance(Class interfaceClass, String implClassName) {
		return loader.newProxyInstance(interfaceClass, implClassName);
	}
}