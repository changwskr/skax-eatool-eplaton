-- MBC 시스템 기초 데이터
-- 생성일: 2024년
-- 설명: MBC 시스템의 기본 데이터 삽입

-- ========================================
-- 사용자 관리 기초 데이터
-- ========================================

-- 사용자 정보 기초 데이터
INSERT INTO USER_INFO (USER_ID, USER_NAME, EMAIL, PHONE, ROLE, STATUS) VALUES
('USER001', '관리자', 'admin@mbc.com', '010-1234-5678', 'ADMIN', 'ACTIVE'),
('USER002', '김철수', 'kim@mbc.com', '010-2345-6789', 'USER', 'ACTIVE'),
('USER003', '이영희', 'lee@mbc.com', '010-3456-7890', 'USER', 'ACTIVE'),
('USER004', '박민수', 'park@mbc.com', '010-4567-8901', 'MANAGER', 'ACTIVE'),
('USER005', '정수진', 'jung@mbc.com', '010-5678-9012', 'USER', 'INACTIVE'),
('USER006', '최영수', 'choi@mbc.com', '010-6789-0123', 'USER', 'ACTIVE'),
('USER007', '한미영', 'han@mbc.com', '010-7890-1234', 'MANAGER', 'ACTIVE'),
('USER008', '송태호', 'song@mbc.com', '010-8901-2345', 'USER', 'ACTIVE'),
('USER009', '임지현', 'lim@mbc.com', '010-9012-3456', 'USER', 'ACTIVE'),
('USER010', '강동원', 'kang@mbc.com', '010-0123-4567', 'ADMIN', 'ACTIVE')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- 사용자 상세 정보 기초 데이터
INSERT INTO USER_DETAIL (USER_ID, DEPARTMENT, POSITION, EMPLOYEE_ID, BIRTH_DATE, ADDRESS, EMERGENCY_CONTACT, EMERGENCY_CONTACT_NAME) VALUES
('USER001', '시스템관리팀', '시스템관리자', 'EMP001', '1980-01-15', '서울시 강남구 테헤란로 123', '010-1111-1111', '김관리'),
('USER002', '개발팀', '개발자', 'EMP002', '1985-03-20', '서울시 서초구 서초대로 456', '010-2222-2222', '김철수부'),
('USER003', '디자인팀', 'UI/UX 디자이너', 'EMP003', '1990-07-10', '서울시 마포구 와우산로 789', '010-3333-3333', '이영희부'),
('USER004', '기획팀', '팀장', 'EMP004', '1982-11-05', '서울시 종로구 종로 321', '010-4444-4444', '박민수부'),
('USER005', '영업팀', '영업사원', 'EMP005', '1988-09-25', '서울시 강서구 화곡로 654', '010-5555-5555', '정수진부'),
('USER006', '개발팀', '시니어 개발자', 'EMP006', '1983-12-03', '서울시 송파구 올림픽로 987', '010-6666-6666', '최영수부'),
('USER007', '마케팅팀', '마케팅 매니저', 'EMP007', '1987-06-18', '서울시 영등포구 여의대로 147', '010-7777-7777', '한미영부'),
('USER008', '개발팀', '주니어 개발자', 'EMP008', '1992-04-12', '서울시 성동구 왕십리로 258', '010-8888-8888', '송태호부'),
('USER009', '디자인팀', '그래픽 디자이너', 'EMP009', '1991-08-30', '서울시 광진구 능동로 369', '010-9999-9999', '임지현부'),
('USER010', '시스템관리팀', '보안관리자', 'EMP010', '1981-02-14', '서울시 중구 을지로 741', '010-0000-0000', '강동원부')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- 사용자 권한 기초 데이터
INSERT INTO USER_PERMISSION (PERMISSION_ID, USER_ID, PERMISSION_TYPE, PERMISSION_VALUE, GRANTED_BY) VALUES
('PERM001', 'USER001', 'SYSTEM_ADMIN', 'ALL', 'SYSTEM'),
('PERM002', 'USER001', 'USER_MANAGEMENT', 'ALL', 'SYSTEM'),
('PERM003', 'USER001', 'ACCOUNT_MANAGEMENT', 'ALL', 'SYSTEM'),
('PERM004', 'USER002', 'ACCOUNT_READ', 'OWN', 'USER001'),
('PERM005', 'USER003', 'ACCOUNT_READ', 'OWN', 'USER001'),
('PERM006', 'USER004', 'USER_MANAGEMENT', 'DEPARTMENT', 'USER001'),
('PERM007', 'USER004', 'ACCOUNT_MANAGEMENT', 'DEPARTMENT', 'USER001'),
('PERM008', 'USER006', 'ACCOUNT_READ', 'ALL', 'USER001'),
('PERM009', 'USER007', 'REPORT_ACCESS', 'ALL', 'USER001'),
('PERM010', 'USER010', 'SECURITY_ADMIN', 'ALL', 'SYSTEM')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 계정 관리 기초 데이터
-- ========================================

-- 계정 정보 기초 데이터
INSERT INTO ACCOUNT (ACCOUNT_NUMBER, NAME, IDENTIFICATION_NUMBER, INTEREST_RATE, LAST_TRANSACTION, PASSWORD, NET_AMOUNT, ACCOUNT_TYPE, STATUS, CURRENCY, CREATED_DATE, UPDATED_DATE) VALUES
('123-456-789', '김철수 저축계좌', '123456-1234567', 2.5, CURRENT_TIMESTAMP, 'password123', 1000000.00, 'SAVINGS', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-790', '이영희 당좌계좌', '234567-2345678', 1.0, CURRENT_TIMESTAMP, 'password456', 500000.00, 'CHECKING', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-791', '박민수 투자계좌', '345678-3456789', 3.5, CURRENT_TIMESTAMP, 'password789', 2500000.00, 'INVESTMENT', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-792', '정수진 저축계좌', '456789-4567890', 2.8, CURRENT_TIMESTAMP, 'password012', 5000000.00, 'SAVINGS', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-793', '관리자 계좌', '567890-5678901', 1.5, CURRENT_TIMESTAMP, 'password345', 750000.00, 'CHECKING', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-794', '최영수 투자계좌', '678901-6789012', 4.2, CURRENT_TIMESTAMP, 'password678', 3000000.00, 'INVESTMENT', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-795', '한미영 저축계좌', '789012-7890123', 2.3, CURRENT_TIMESTAMP, 'password901', 1200000.00, 'SAVINGS', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('123-456-796', '송태호 당좌계좌', '890123-8901234', 1.2, CURRENT_TIMESTAMP, 'password234', 800000.00, 'CHECKING', 'ACTIVE', 'KRW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- 사용자-계정 연결 기초 데이터
INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_NUMBER, RELATION_TYPE) VALUES
('USER001', '123-456-793', 'OWNER'),
('USER002', '123-456-789', 'OWNER'),
('USER003', '123-456-790', 'OWNER'),
('USER004', '123-456-791', 'OWNER'),
('USER005', '123-456-792', 'OWNER'),
('USER006', '123-456-794', 'OWNER'),
('USER007', '123-456-795', 'OWNER'),
('USER008', '123-456-796', 'OWNER'),
('USER001', '123-456-789', 'JOINT'),
('USER002', '123-456-790', 'JOINT')
ON DUPLICATE KEY UPDATE CREATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 시스템 코드 기초 데이터
-- ========================================

-- 시스템 코드 기초 데이터
INSERT INTO SYSTEM_CODE (CODE_ID, CODE_TYPE, CODE_VALUE, CODE_NAME, CODE_DESC, SORT_ORDER, USE_YN) VALUES
-- 사용자 역할 코드
('CODE001', 'USER_ROLE', 'ADMIN', '관리자', '시스템 관리자', 1, 'Y'),
('CODE002', 'USER_ROLE', 'MANAGER', '매니저', '부서 매니저', 2, 'Y'),
('CODE003', 'USER_ROLE', 'USER', '일반사용자', '일반 사용자', 3, 'Y'),
('CODE004', 'USER_ROLE', 'GUEST', '게스트', '게스트 사용자', 4, 'Y'),

-- 사용자 상태 코드
('CODE005', 'USER_STATUS', 'ACTIVE', '활성', '활성 사용자', 1, 'Y'),
('CODE006', 'USER_STATUS', 'INACTIVE', '비활성', '비활성 사용자', 2, 'Y'),
('CODE007', 'USER_STATUS', 'SUSPENDED', '정지', '정지된 사용자', 3, 'Y'),
('CODE008', 'USER_STATUS', 'DELETED', '삭제', '삭제된 사용자', 4, 'N'),

-- 부서 코드
('CODE009', 'DEPARTMENT', 'SYSTEM', '시스템관리팀', '시스템 관리 및 운영', 1, 'Y'),
('CODE010', 'DEPARTMENT', 'DEVELOPMENT', '개발팀', '소프트웨어 개발', 2, 'Y'),
('CODE011', 'DEPARTMENT', 'DESIGN', '디자인팀', 'UI/UX 디자인', 3, 'Y'),
('CODE012', 'DEPARTMENT', 'PLANNING', '기획팀', '서비스 기획', 4, 'Y'),
('CODE013', 'DEPARTMENT', 'SALES', '영업팀', '영업 및 마케팅', 5, 'Y'),
('CODE014', 'DEPARTMENT', 'MARKETING', '마케팅팀', '마케팅 전략', 6, 'Y'),

-- 직급 코드
('CODE015', 'POSITION', 'DIRECTOR', '이사', '이사급', 1, 'Y'),
('CODE016', 'POSITION', 'MANAGER', '팀장', '팀장급', 2, 'Y'),
('CODE017', 'POSITION', 'SENIOR', '시니어', '시니어급', 3, 'Y'),
('CODE018', 'POSITION', 'JUNIOR', '주니어', '주니어급', 4, 'Y'),
('CODE019', 'POSITION', 'STAFF', '사원', '사원급', 5, 'Y'),

-- 계정 타입 코드
('CODE020', 'ACCOUNT_TYPE', 'SAVINGS', '저축예금', '저축 예금 계좌', 1, 'Y'),
('CODE021', 'ACCOUNT_TYPE', 'CHECKING', '당좌예금', '당좌 예금 계좌', 2, 'Y'),
('CODE022', 'ACCOUNT_TYPE', 'INVESTMENT', '투자계좌', '투자 계좌', 3, 'Y'),
('CODE023', 'ACCOUNT_TYPE', 'LOAN', '대출계좌', '대출 계좌', 4, 'Y'),

-- 계정 상태 코드
('CODE024', 'ACCOUNT_STATUS', 'ACTIVE', '활성', '활성 계좌', 1, 'Y'),
('CODE025', 'ACCOUNT_STATUS', 'INACTIVE', '비활성', '비활성 계좌', 2, 'Y'),
('CODE026', 'ACCOUNT_STATUS', 'FROZEN', '동결', '동결된 계좌', 3, 'Y'),
('CODE027', 'ACCOUNT_STATUS', 'CLOSED', '해지', '해지된 계좌', 4, 'Y'),

-- 통화 코드
('CODE028', 'CURRENCY', 'KRW', '원화', '대한민국 원화', 1, 'Y'),
('CODE029', 'CURRENCY', 'USD', '달러', '미국 달러', 2, 'Y'),
('CODE030', 'CURRENCY', 'EUR', '유로', '유럽 연합 유로', 3, 'Y'),
('CODE031', 'CURRENCY', 'JPY', '엔화', '일본 엔화', 4, 'Y'),

-- 거래 타입 코드
('CODE032', 'TRANSACTION_TYPE', 'DEPOSIT', '입금', '계좌 입금', 1, 'Y'),
('CODE033', 'TRANSACTION_TYPE', 'WITHDRAWAL', '출금', '계좌 출금', 2, 'Y'),
('CODE034', 'TRANSACTION_TYPE', 'TRANSFER', '이체', '계좌 이체', 3, 'Y'),
('CODE035', 'TRANSACTION_TYPE', 'PAYMENT', '결제', '결제 거래', 4, 'Y'),

-- 거래 상태 코드
('CODE036', 'TRANSACTION_STATUS', 'PENDING', '대기', '거래 대기', 1, 'Y'),
('CODE037', 'TRANSACTION_STATUS', 'COMPLETED', '완료', '거래 완료', 2, 'Y'),
('CODE038', 'TRANSACTION_STATUS', 'FAILED', '실패', '거래 실패', 3, 'Y'),
('CODE039', 'TRANSACTION_STATUS', 'CANCELLED', '취소', '거래 취소', 4, 'Y'),

-- 로그 레벨 코드
('CODE040', 'LOG_LEVEL', 'DEBUG', '디버그', '디버그 레벨', 1, 'Y'),
('CODE041', 'LOG_LEVEL', 'INFO', '정보', '정보 레벨', 2, 'Y'),
('CODE042', 'LOG_LEVEL', 'WARN', '경고', '경고 레벨', 3, 'Y'),
('CODE043', 'LOG_LEVEL', 'ERROR', '오류', '오류 레벨', 4, 'Y')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 역할 및 권한 기초 데이터
-- ========================================

-- 역할 정보 기초 데이터
INSERT INTO ROLE (ROLE_ID, ROLE_NAME, ROLE_DESC, USE_YN) VALUES
('ROLE001', '시스템관리자', '시스템 전체 관리 권한', 'Y'),
('ROLE002', '부서관리자', '부서별 관리 권한', 'Y'),
('ROLE003', '일반사용자', '기본 사용자 권한', 'Y'),
('ROLE004', '게스트', '제한된 사용자 권한', 'Y'),
('ROLE005', '보안관리자', '보안 관련 관리 권한', 'Y')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- 사용자 역할 연결 기초 데이터
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES
('USER001', 'ROLE001'),
('USER002', 'ROLE003'),
('USER003', 'ROLE003'),
('USER004', 'ROLE002'),
('USER005', 'ROLE003'),
('USER006', 'ROLE003'),
('USER007', 'ROLE002'),
('USER008', 'ROLE003'),
('USER009', 'ROLE003'),
('USER010', 'ROLE005')
ON DUPLICATE KEY UPDATE CREATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 메뉴 및 권한 기초 데이터
-- ========================================

-- 메뉴 정보 기초 데이터
INSERT INTO MENU (MENU_ID, MENU_NAME, MENU_URL, PARENT_MENU_ID, SORT_ORDER, USE_YN) VALUES
-- 메인 메뉴
('MENU001', '대시보드', '/dashboard', NULL, 1, 'Y'),
('MENU002', '사용자관리', '/users', NULL, 2, 'Y'),
('MENU003', '계정관리', '/accounts', NULL, 3, 'Y'),
('MENU004', '시스템관리', '/system', NULL, 4, 'Y'),

-- 사용자관리 하위 메뉴
('MENU005', '사용자목록', '/users/list', 'MENU002', 1, 'Y'),
('MENU006', '사용자등록', '/users/register', 'MENU002', 2, 'Y'),
('MENU007', '역할관리', '/users/roles', 'MENU002', 3, 'Y'),

-- 계정관리 하위 메뉴
('MENU008', '계정목록', '/accounts/list', 'MENU003', 1, 'Y'),
('MENU009', '계정등록', '/accounts/register', 'MENU003', 2, 'Y'),
('MENU010', '거래내역', '/accounts/transactions', 'MENU003', 3, 'Y'),

-- 시스템관리 하위 메뉴
('MENU011', '시스템코드', '/system/codes', 'MENU004', 1, 'Y'),
('MENU012', '메뉴관리', '/system/menus', 'MENU004', 2, 'Y'),
('MENU013', '로그관리', '/system/logs', 'MENU004', 3, 'Y'),
('MENU014', '설정관리', '/system/config', 'MENU004', 4, 'Y')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- 역할 메뉴 권한 기초 데이터
INSERT INTO ROLE_MENU (ROLE_ID, MENU_ID, PERMISSION_TYPE) VALUES
-- 시스템관리자 권한 (모든 메뉴)
('ROLE001', 'MENU001', 'READ'),
('ROLE001', 'MENU002', 'READ'),
('ROLE001', 'MENU003', 'READ'),
('ROLE001', 'MENU004', 'READ'),
('ROLE001', 'MENU005', 'READ'),
('ROLE001', 'MENU006', 'WRITE'),
('ROLE001', 'MENU007', 'WRITE'),
('ROLE001', 'MENU008', 'READ'),
('ROLE001', 'MENU009', 'WRITE'),
('ROLE001', 'MENU010', 'READ'),
('ROLE001', 'MENU011', 'WRITE'),
('ROLE001', 'MENU012', 'WRITE'),
('ROLE001', 'MENU013', 'READ'),
('ROLE001', 'MENU014', 'WRITE'),

-- 부서관리자 권한
('ROLE002', 'MENU001', 'READ'),
('ROLE002', 'MENU002', 'READ'),
('ROLE002', 'MENU003', 'READ'),
('ROLE002', 'MENU005', 'READ'),
('ROLE002', 'MENU006', 'WRITE'),
('ROLE002', 'MENU008', 'READ'),
('ROLE002', 'MENU009', 'WRITE'),
('ROLE002', 'MENU010', 'READ'),

-- 일반사용자 권한
('ROLE003', 'MENU001', 'READ'),
('ROLE003', 'MENU003', 'READ'),
('ROLE003', 'MENU008', 'READ'),
('ROLE003', 'MENU010', 'READ'),

-- 보안관리자 권한
('ROLE005', 'MENU001', 'READ'),
('ROLE005', 'MENU004', 'READ'),
('ROLE005', 'MENU013', 'READ'),
('ROLE005', 'MENU014', 'WRITE')
ON DUPLICATE KEY UPDATE CREATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 거래 내역 기초 데이터
-- ========================================

-- 거래 내역 기초 데이터
INSERT INTO TRANSACTION (TRANSACTION_ID, ACCOUNT_NUMBER, TRANSACTION_TYPE, AMOUNT, CURRENCY, DESCRIPTION, STATUS) VALUES
('TXN001', '123-456-789', 'DEPOSIT', 1000000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN002', '123-456-790', 'DEPOSIT', 500000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN003', '123-456-791', 'DEPOSIT', 2500000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN004', '123-456-792', 'DEPOSIT', 5000000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN005', '123-456-793', 'DEPOSIT', 750000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN006', '123-456-794', 'DEPOSIT', 3000000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN007', '123-456-795', 'DEPOSIT', 1200000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN008', '123-456-796', 'DEPOSIT', 800000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN009', '123-456-789', 'WITHDRAWAL', 100000.00, 'KRW', 'ATM 출금', 'COMPLETED'),
('TXN010', '123-456-790', 'TRANSFER', 50000.00, 'KRW', '계좌 이체', 'COMPLETED'),
('TXN011', '123-456-791', 'DEPOSIT', 300000.00, 'KRW', '급여 입금', 'COMPLETED'),
('TXN012', '123-456-792', 'WITHDRAWAL', 200000.00, 'KRW', '카드 결제', 'COMPLETED')
ON DUPLICATE KEY UPDATE CREATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 시스템 설정 기초 데이터
-- ========================================

-- 시스템 설정 기초 데이터
INSERT INTO SYSTEM_CONFIG (CONFIG_ID, CONFIG_KEY, CONFIG_VALUE, CONFIG_DESC, USE_YN) VALUES
('CONFIG001', 'system.name', 'MBC System', '시스템 이름', 'Y'),
('CONFIG002', 'system.version', '2.0.0', '시스템 버전', 'Y'),
('CONFIG003', 'system.description', 'MBC 프로젝트 시스템', '시스템 설명', 'Y'),
('CONFIG004', 'app.debug', 'true', '디버그 모드', 'Y'),
('CONFIG005', 'app.data-access.type', 'jdbc', '데이터 접근 방식', 'Y'),
('CONFIG006', 'mbc.business.transaction-timeout', '30', '트랜잭션 타임아웃(초)', 'Y'),
('CONFIG007', 'mbc.business.max-retry-count', '3', '최대 재시도 횟수', 'Y'),
('CONFIG008', 'mbc.business.audit-logging', 'true', '감사 로깅 활성화', 'Y'),
('CONFIG009', 'mbc.cache.enabled', 'false', '캐시 활성화', 'Y'),
('CONFIG010', 'mbc.cache.ttl', '300', '캐시 TTL(초)', 'Y'),
('CONFIG011', 'mbc.cache.max-size', '1000', '캐시 최대 크기', 'Y'),
('CONFIG012', 'user.session.timeout', '1800', '사용자 세션 타임아웃(초)', 'Y'),
('CONFIG013', 'user.password.expiry.days', '90', '비밀번호 만료일수', 'Y'),
('CONFIG014', 'user.login.max.attempts', '5', '최대 로그인 시도 횟수', 'Y'),
('CONFIG015', 'user.account.lockout.duration', '30', '계정 잠금 시간(분)', 'Y')
ON DUPLICATE KEY UPDATE UPDATED_DATE = CURRENT_TIMESTAMP;

-- ========================================
-- 시스템 로그 기초 데이터
-- ========================================

-- 시스템 로그 기초 데이터
INSERT INTO SYSTEM_LOG (LOG_ID, USER_ID, LOG_TYPE, LOG_LEVEL, MESSAGE, IP_ADDRESS) VALUES
('LOG001', 'USER001', 'LOGIN', 'INFO', '관리자 로그인 성공', '127.0.0.1'),
('LOG002', 'USER001', 'SYSTEM', 'INFO', '시스템 초기화 완료', '127.0.0.1'),
('LOG003', 'USER002', 'LOGIN', 'INFO', '사용자 로그인 성공', '192.168.1.100'),
('LOG004', 'USER003', 'LOGIN', 'INFO', '사용자 로그인 성공', '192.168.1.101'),
('LOG005', 'USER001', 'USER', 'INFO', '새 사용자 등록: USER004', '127.0.0.1'),
('LOG006', 'USER001', 'ACCOUNT', 'INFO', '새 계정 등록: 123-456-792', '127.0.0.1'),
('LOG007', 'USER002', 'TRANSACTION', 'INFO', '거래 완료: TXN006', '192.168.1.100'),
('LOG008', 'USER003', 'TRANSACTION', 'INFO', '거래 완료: TXN007', '192.168.1.101'),
('LOG009', 'USER004', 'LOGIN', 'INFO', '매니저 로그인 성공', '192.168.1.102'),
('LOG010', 'USER005', 'LOGIN', 'WARN', '비활성 사용자 로그인 시도', '192.168.1.103'),
('LOG011', 'USER006', 'USER', 'INFO', '사용자 정보 수정: USER006', '192.168.1.104'),
('LOG012', 'USER007', 'SYSTEM', 'INFO', '시스템 설정 변경', '192.168.1.105')
ON DUPLICATE KEY UPDATE CREATED_DATE = CURRENT_TIMESTAMP; 