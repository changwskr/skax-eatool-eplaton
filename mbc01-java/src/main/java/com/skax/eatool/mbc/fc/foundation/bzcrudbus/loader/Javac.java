package com.skax.eatool.mbc.fc.foundation.bzcrudbus.loader;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Java 컴파일러 래퍼 클래스
 * 
 * 프로그램명: Javac.java
 * 설명: com.sun.tools.javac.Main의 사용을 쉽게 하기 위한 래퍼 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team (원작자: liyang)
 * 
 * 주요 기능:
 * - Java 소스 파일 컴파일
 * - 컴파일 옵션 설정
 * - 컴파일 에러 처리
 * - 클래스패스 및 출력 디렉토리 관리
 * 
 * @version 1.0
 */
public final class Javac {

	/** 클래스패스 */
	String classpath;

	/** 출력 디렉토리 */
	String outputdir;

	/** 소스패스 */
	String sourcepath;

	/** 부트 클래스패스 */
	String bootclasspath;

	/** 확장 디렉토리 */
	String extdirs;

	/** 인코딩 */
	String encoding;

	/** 타겟 버전 */
	String target;

	/**
	 * 클래스패스와 출력 디렉토리를 지정하는 생성자
	 * 
	 * @param classpath 클래스패스
	 * @param outputdir 출력 디렉토리
	 */
	public Javac(String classpath, String outputdir) {
		this.classpath = classpath;
		this.outputdir = outputdir;
	}

	/**
	 * 주어진 소스 파일들을 컴파일한다.
	 * 
	 * @param srcFiles 소스 파일 배열
	 * @return 성공하면 null, 실패하면 컴파일 에러 메시지
	 */
	public String compile(String[] srcFiles) {
		StringWriter err = new StringWriter();
		PrintWriter errPrinter = new PrintWriter(err);

		String args[] = buildJavacArgs(srcFiles);
		int resultCode = com.sun.tools.javac.Main.compile(args, errPrinter);

		errPrinter.close();
		return (resultCode == 0) ? null : err.toString();
	}

	/**
	 * 주어진 소스 파일들을 컴파일한다.
	 * 
	 * @param srcFiles 소스 파일 배열
	 * @return 성공하면 null, 실패하면 컴파일 에러 메시지
	 */
	public String compile(File[] srcFiles) {
		String paths[] = new String[srcFiles.length];
		for (int i = 0; i < paths.length; i++) {
			paths[i] = srcFiles[i].getAbsolutePath();
		}
		return compile(paths);
	}

	/**
	 * javac 명령행 인수를 구성한다.
	 * 
	 * @param srcFiles 소스 파일 배열
	 * @return javac 명령행 인수 배열
	 */
	private String[] buildJavacArgs(String[] srcFiles) {
		ArrayList args = new ArrayList();

		if (classpath != null) {
			args.add("-classpath");
			args.add(classpath);
		}
		if (outputdir != null) {
			args.add("-d");
			args.add(outputdir);
		}
		if (sourcepath != null) {
			args.add("-sourcepath");
			args.add(sourcepath);
		}
		if (bootclasspath != null) {
			args.add("-bootclasspath");
			args.add(bootclasspath);
		}
		if (extdirs != null) {
			args.add("-extdirs");
			args.add(extdirs);
		}
		if (encoding != null) {
			args.add("-encoding");
			args.add(encoding);
		}
		if (target != null) {
			args.add("-target");
			args.add(target);
		}

		for (int i = 0; i < srcFiles.length; i++) {
			args.add(srcFiles[i]);
		}

		return (String[]) args.toArray(new String[args.size()]);
	}

	/**
	 * 부트 클래스패스를 반환한다.
	 * 
	 * @return 부트 클래스패스
	 */
	public String getBootclasspath() {
		return bootclasspath;
	}

	/**
	 * 부트 클래스패스를 설정한다.
	 * 
	 * @param bootclasspath 부트 클래스패스
	 */
	public void setBootclasspath(String bootclasspath) {
		this.bootclasspath = bootclasspath;
	}

	/**
	 * 클래스패스를 반환한다.
	 * 
	 * @return 클래스패스
	 */
	public String getClasspath() {
		return classpath;
	}

	/**
	 * 클래스패스를 설정한다.
	 * 
	 * @param classpath 클래스패스
	 */
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

	/**
	 * 인코딩을 반환한다.
	 * 
	 * @return 인코딩
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 인코딩을 설정한다.
	 * 
	 * @param encoding 인코딩
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 확장 디렉토리를 반환한다.
	 * 
	 * @return 확장 디렉토리
	 */
	public String getExtdirs() {
		return extdirs;
	}

	/**
	 * 확장 디렉토리를 설정한다.
	 * 
	 * @param extdirs 확장 디렉토리
	 */
	public void setExtdirs(String extdirs) {
		this.extdirs = extdirs;
	}

	/**
	 * 출력 디렉토리를 반환한다.
	 * 
	 * @return 출력 디렉토리
	 */
	public String getOutputdir() {
		return outputdir;
	}

	/**
	 * 출력 디렉토리를 설정한다.
	 * 
	 * @param outputdir 출력 디렉토리
	 */
	public void setOutputdir(String outputdir) {
		this.outputdir = outputdir;
	}

	/**
	 * 소스패스를 반환한다.
	 * 
	 * @return 소스패스
	 */
	public String getSourcepath() {
		return sourcepath;
	}

	/**
	 * 소스패스를 설정한다.
	 * 
	 * @param sourcepath 소스패스
	 */
	public void setSourcepath(String sourcepath) {
		this.sourcepath = sourcepath;
	}

	/**
	 * 타겟 버전을 반환한다.
	 * 
	 * @return 타겟 버전
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 타겟 버전을 설정한다.
	 * 
	 * @param target 타겟 버전
	 */
	public void setTarget(String target) {
		this.target = target;
	}
}
