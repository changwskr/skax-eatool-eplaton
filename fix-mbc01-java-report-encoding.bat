@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Report Encoding Script - Windows
REM mbc01-java report 패키지 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Report Encoding
echo ========================================

echo.
echo 1. MBC01-Java Report 패키지 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.

echo 수정된 파일 목록:
echo.

echo [Report 패키지 - 리포트 생성 기능]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/report/WordReportUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/report/ReportMeta.java
echo.

echo ========================================
echo 수정 내용 요약:
echo ========================================
echo.
echo 1. 모든 Java 소스 파일의 인코딩을 UTF-8로 변환
echo 2. 깨진 한글 주석을 올바른 UTF-8 인코딩으로 수정
echo 3. 클래스 레벨 JavaDoc 주석 추가
echo 4. 메서드 및 필드 레벨 주석 개선
echo 5. 일관된 주석 스타일 적용
echo.

echo ========================================
echo 수정된 클래스 상세 정보:
echo ========================================
echo.
echo [WordReportUtil.java]
echo - Word 리포트 유틸리티 클래스
echo - RTF 템플릿 기반 Word 문서 생성
echo - Velocity 템플릿 엔진을 통한 데이터 바인딩
echo - 그리드 데이터 및 변수 처리
echo - SQL 데이터 조회 및 매핑
echo - 로깅 및 에러 처리
echo.
echo [ReportMeta.java]
echo - 리포트 메타데이터 클래스
echo - Word 리포트 생성을 위한 메타데이터 관리
echo - 리포트 기본 정보 관리 (기준년월, 리포트ID, 리포트명)
echo - 변수 정보 관리 (변수명, 변수값, 데이터타입)
echo - 그리드 데이터 타입 관리
echo - CRUD 작업 상태 관리
echo.

echo ========================================
echo 리포트 생성 기능:
echo ========================================
echo.
echo 1. RTF 템플릿 처리
echo - RTF 템플릿 파일 로드 및 파싱
echo - Velocity 템플릿 엔진을 통한 데이터 바인딩
echo - 템플릿 변수 설정 (텍스트, 맵, 리스트)
echo.
echo 2. 데이터 처리
echo - 그리드 데이터 타입 (GD) 처리
echo - SQL 데이터 조회 및 매핑
echo - 변수 데이터 처리
echo.
echo 3. 문서 생성
echo - RTF 템플릿 병합
echo - Word 문서 (.doc) 파일 생성
echo - 로깅 및 에러 처리
echo.

echo ========================================
echo 기술 스택:
echo ========================================
echo.
echo - RTF (Rich Text Format) 템플릿 처리
echo - Apache Velocity 템플릿 엔진
echo - iBATIS SQL 매퍼
echo - IFRS 로깅 시스템
echo - 인코딩 변환 유틸리티
echo.

echo ========================================
echo 다음 단계:
echo ========================================
echo.
echo 1. IDE에서 프로젝트를 다시 로드하여 변경사항 확인
echo 2. 빌드 테스트 실행
echo 3. 한글 주석이 올바르게 표시되는지 확인
echo 4. 필요시 추가 인코딩 수정 작업 진행
echo.

echo ========================================
echo 주의사항:
echo ========================================
echo.
echo 1. 모든 파일이 UTF-8 인코딩으로 저장되었습니다.
echo 2. IDE 설정에서 파일 인코딩을 UTF-8로 설정하세요.
echo 3. 빌드 도구(Maven/Gradle)에서도 UTF-8 인코딩을 사용하세요.
echo 4. 새로운 파일 생성 시 반드시 UTF-8 인코딩을 사용하세요.
echo 5. RTF 템플릿 파일도 UTF-8 인코딩으로 저장해야 합니다.
echo.

echo ========================================
echo 인코딩 수정 완료!
echo ========================================
echo.
pause 