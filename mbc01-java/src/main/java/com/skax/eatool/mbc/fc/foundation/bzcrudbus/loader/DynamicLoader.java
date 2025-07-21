package com.skax.eatool.mbc.fc.foundation.bzcrudbus.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 동적 클래스 로더 클래스
 * 
 * 프로그램명: DynamicLoader.java
 * 설명: 런타임에 Java 소스 코드를 컴파일하고 동적으로 로드하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 동적 Java 소스 코드 컴파일
 * - 런타임 클래스 로딩
 * - 소스 디렉토리 관리
 * - 프록시 인스턴스 생성
 * - 클래스 변경 감지 및 재로딩
 * 
 * @version 1.0
 */
public final class DynamicLoader {

	/** 컴파일 클래스패스 */
	private String compileClasspath;

	/** 부모 클래스 로더 */
	private ClassLoader parentClassLoader;

	/** 소스 디렉토리 리스트 */
	private ArrayList sourceDirs = new ArrayList();

	/** 로드된 클래스 맵 (class name => LoadedClass) */
	private HashMap loadedClasses = new HashMap();

	/**
	 * 기본 생성자
	 * 현재 스레드의 컨텍스트 클래스 로더를 사용한다.
	 */
	public DynamicLoader() {
		this(Thread.currentThread().getContextClassLoader());
	}

	/**
	 * 부모 클래스 로더를 지정하는 생성자
	 * 
	 * @param parentClassLoader 부모 클래스 로더
	 */
	public DynamicLoader(ClassLoader parentClassLoader) {
		this(extractClasspath(parentClassLoader), parentClassLoader);

	}

	/**
	 * 컴파일 클래스패스와 부모 클래스 로더를 지정하는 생성자
	 * 
	 * @param compileClasspath  동적 클래스 컴파일에 사용할 클래스패스
	 * @param parentClassLoader 동적 클래스들을 로드하는 클래스 로더의 부모
	 */
	public DynamicLoader(String compileClasspath, ClassLoader parentClassLoader) {
		this.compileClasspath = compileClasspath;
		this.parentClassLoader = parentClassLoader;
	}

	/**
	 * 동적 Java 코드의 소스를 포함하는 디렉토리를 추가한다.
	 * 
	 * @param srcDir 소스 디렉토리
	 * @return 추가가 성공하면 true
	 */
	public boolean addSourceDir(File srcDir) {

		try {
			srcDir = srcDir.getCanonicalFile();
		} catch (IOException e) {
			// ignore
		}

		synchronized (sourceDirs) {

			// 존재 여부 확인
			for (int i = 0; i < sourceDirs.size(); i++) {
				SourceDir src = (SourceDir) sourceDirs.get(i);
				if (src.srcDir.equals(srcDir)) {
					return false;
				}
			}

			// 새로 추가
			SourceDir src = new SourceDir(srcDir);
			sourceDirs.add(src);

			info("Add source dir " + srcDir);
		}

		return true;
	}

	/**
	 * 이름으로 최신 동적 클래스를 반환한다.
	 * 
	 * @param className 클래스 이름
	 * @return 로드된 클래스
	 * @throws ClassNotFoundException 소스 파일을 찾을 수 없거나 컴파일 오류가 발생한 경우
	 */
	public Class loadClass(String className) throws ClassNotFoundException {

		LoadedClass loadedClass = null;
		synchronized (loadedClasses) {
			loadedClass = (LoadedClass) loadedClasses.get(className);
		}

		// 클래스의 첫 번째 접근
		if (loadedClass == null) {

			String resource = className.replace('.', '/') + ".java";
			SourceDir src = locateResource(resource);
			if (src == null) {
				throw new ClassNotFoundException("DynamicLoader class not found " + className);
			}

			synchronized (this) {

				// 컴파일하고 클래스 로드
				loadedClass = new LoadedClass(className, src);

				synchronized (loadedClasses) {
					loadedClasses.put(className, loadedClass);
				}
			}

			return loadedClass.clazz;
		}

		// 이후 접근
		if (loadedClass.isChanged()) {
			// 언로드하고 다시 로드
			unload(loadedClass.srcDir);
			return loadClass(className);
		}

		return loadedClass.clazz;
	}

	/**
	 * 리소스를 찾는다.
	 * 
	 * @param resource 리소스 경로
	 * @return 찾은 SourceDir 또는 null
	 */
	private SourceDir locateResource(String resource) {
		for (int i = 0; i < sourceDirs.size(); i++) {
			SourceDir src = (SourceDir) sourceDirs.get(i);
			if (new File(src.srcDir, resource).exists()) {
				return src;
			}
		}
		return null;
	}

	/**
	 * 소스 디렉토리의 클래스들을 언로드한다.
	 * 
	 * @param src 소스 디렉토리
	 */
	private void unload(SourceDir src) {
		// 로드된 클래스들 정리
		synchronized (loadedClasses) {
			for (Iterator iter = loadedClasses.values().iterator(); iter
					.hasNext();) {
				LoadedClass loadedClass = (LoadedClass) iter.next();
				if (loadedClass.srcDir == src) {
					iter.remove();
				}
			}
		}

		// 새로운 클래스 로더 생성
		src.recreateClassLoader();
	}

	/**
	 * 추가된 소스 디렉토리에서 리소스를 가져온다.
	 * 
	 * @param resource 리소스 경로
	 * @return 리소스 URL, 찾을 수 없으면 null
	 */
	public URL getResource(String resource) {
		try {

			SourceDir src = locateResource(resource);
			return src == null ? null : new File(src.srcDir, resource).toURL();

		} catch (MalformedURLException e) {
			// should not happen
			return null;
		}
	}

	/**
	 * 추가된 소스 디렉토리에서 리소스 스트림을 가져온다.
	 * 
	 * @param resource 리소스 경로
	 * @return 리소스 스트림, 찾을 수 없으면 null
	 */
	public InputStream getResourceAsStream(String resource) {
		try {

			SourceDir src = locateResource(resource);
			return src == null ? null
					: new FileInputStream(new File(
							src.srcDir, resource));

		} catch (FileNotFoundException e) {
			// should not happen
			return null;
		}
	}

	/**
	 * 지정된 액세스 인터페이스를 구현하고 들어오는 호출을 지정된 동적 구현에 위임하는 프록시 인스턴스를 생성한다.
	 * 동적 구현은 런타임에 변경될 수 있으며, 변경 사항은 자동으로 감지되고 적용된다.
	 * 
	 * @param interfaceClass 인터페이스 클래스
	 * @param implClassName  구현 클래스 이름
	 * @return 프록시 인스턴스
	 * @throws RuntimeException 런타임 예외
	 */
	public Object newProxyInstance(Class interfaceClass, String implClassName) throws RuntimeException {
		MyInvocationHandler handler = new MyInvocationHandler(implClassName);
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, handler);
	}

	public Object newInstance(String className) throws RuntimeException {
		try {
			Class clz = loadClass(className);
			return clz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private class SourceDir {
		File srcDir;

		File binDir;

		Javac javac;

		URLClassLoader classLoader;

		SourceDir(File srcDir) {
			this.srcDir = srcDir;

			String subdir = srcDir.getAbsolutePath().replace(':', '_').replace('/', '_').replace('\\', '_');
			this.binDir = new File(System.getProperty("java.io.tmpdir"), "dynacode/" + subdir);
			this.binDir.mkdirs();

			// prepare compiler
			this.javac = new Javac(compileClasspath, binDir.getAbsolutePath());

			// class loader
			recreateClassLoader();
		}

		void recreateClassLoader() {
			try {
				classLoader = new URLClassLoader(new URL[] { binDir.toURL() }, parentClassLoader);
			} catch (MalformedURLException e) {
				// should not happen
			}
		}

	}

	private static class LoadedClass {
		String className;

		SourceDir srcDir;

		File srcFile;

		File binFile;

		Class clazz;

		long lastModified;

		LoadedClass(String className, SourceDir src) {
			this.className = className;
			this.srcDir = src;

			String path = className.replace('.', '/');
			this.srcFile = new File(src.srcDir, path + ".java");
			this.binFile = new File(src.binDir, path + ".class");

			compileAndLoadClass();
		}

		boolean isChanged() {
			return srcFile.lastModified() != lastModified;
		}

		void compileAndLoadClass() {

			if (clazz != null) {
				return; // class already loaded
			}

			// compile, if required
			String error = null;
			if (binFile.lastModified() < srcFile.lastModified()) {
				error = srcDir.javac.compile(new File[] { srcFile });
			}

			if (error != null) {
				throw new RuntimeException("Failed to compile "
						+ srcFile.getAbsolutePath() + ". Error: " + error);
			}

			try {
				// load class
				clazz = srcDir.classLoader.loadClass(className);

				// load class success, remember timestamp
				lastModified = srcFile.lastModified();

			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Failed to load DynamicLoader class " + srcFile.getAbsolutePath());
			}

			info("Init " + clazz);
		}
	}

	private class MyInvocationHandler implements InvocationHandler {

		String backendClassName;

		Object backend;

		MyInvocationHandler(String className) {
			backendClassName = className;

			try {
				Class clz = loadClass(backendClassName);
				backend = newDynamicLoaderInstance(clz);

			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {

			// check if class has been updated
			Class clz = loadClass(backendClassName);
			if (backend.getClass() != clz) {
				backend = newDynamicLoaderInstance(clz);
			}

			try {
				// invoke on backend
				return method.invoke(backend, args);

			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}

		private Object newDynamicLoaderInstance(Class clz) {
			try {
				return clz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(
						"Failed to new instance of DynamicLoader class "
								+ clz.getName(),
						e);
			}
		}

	}

	/**
	 * Extracts a classpath string from a given class loader. Recognizes only
	 * URLClassLoader.
	 */
	private static String extractClasspath(ClassLoader cl) {
		StringBuffer buf = new StringBuffer();

		while (cl != null) {
			if (cl instanceof URLClassLoader) {
				URL urls[] = ((URLClassLoader) cl).getURLs();
				for (int i = 0; i < urls.length; i++) {
					if (buf.length() > 0) {
						buf.append(File.pathSeparatorChar);
					}
					buf.append(urls[i].getFile().toString());
				}
			}
			cl = cl.getParent();
		}

		System.out.println("extractClasspath : " + buf.toString());

		return buf.toString();
	}

	/**
	 * Log a message.
	 */
	private static void info(String msg) {
		System.out.println("[DynamicLoader] " + msg);
	}

}