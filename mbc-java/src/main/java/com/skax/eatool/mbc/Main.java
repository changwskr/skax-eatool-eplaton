package com.skax.eatool.mbc;

/**
 * MBC 애플리케이션 메인 진입점
 * 
 * 이 클래스는 MBC 애플리케이션의 시작점으로,
 * MbcApplication 클래스를 실행합니다.
 * 
 * @author skax.eatool
 * @version 1.0.0
 * @since 2024
 */
public class Main {

    /**
     * 애플리케이션 메인 메서드
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        System.out.println("=== MBC 애플리케이션 시작 ===");
        System.out.println("Java 버전: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.out.println("작업 디렉토리: " + System.getProperty("user.dir"));
        System.out.println();

        try {
            // MbcApplication 실행
            MbcApplication.main(args);
        } catch (Exception e) {
            System.err.println("애플리케이션 실행 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}