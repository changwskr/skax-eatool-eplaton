-- MBC 시스템 기초 데이터
-- 생성일: 2024년
-- 설명: MBC 시스템의 기본 데이터 삽입

-- 사용자 정보 기초 데이터
INSERT INTO USER_INFO (USER_ID, USER_NAME, EMAIL, PHONE, ROLE, STATUS) VALUES
('USER001', '관리자', 'admin@mbc.com', '010-1234-5678', 'ADMIN', 'ACTIVE'),
('USER002', '김철수', 'kim@mbc.com', '010-2345-6789', 'USER', 'ACTIVE'),
('USER003', '이영희', 'lee@mbc.com', '010-3456-7890', 'USER', 'ACTIVE'),
('USER004', '박민수', 'park@mbc.com', '010-4567-8901', 'MANAGER', 'ACTIVE'),
('USER005', '정수진', 'jung@mbc.com', '010-5678-9012', 'USER', 'INACTIVE');

-- 계정 정보 기초 데이터
INSERT INTO ACCOUNT (ACCOUNT_NUMBER, NAME, IDENTIFICATION_NUMBER, INTEREST_RATE, LAST_TRANSACTION, PASSWORD, NET_AMOUNT) VALUES
('123-456-789', '김철수 저축계좌', '123456-1234567', 2.5, CURRENT_TIMESTAMP, 'password123', '1000000'),
('123-456-790', '이영희 당좌계좌', '234567-2345678', 1.0, CURRENT_TIMESTAMP, 'password456', '500000'),
('123-456-791', '박민수 투자계좌', '345678-3456789', 3.5, CURRENT_TIMESTAMP, 'password789', '2500000'),
('123-456-792', '정수진 저축계좌', '456789-4567890', 2.8, CURRENT_TIMESTAMP, 'password012', '5000000'),
('123-456-793', '관리자 계좌', '567890-5678901', 1.5, CURRENT_TIMESTAMP, 'password345', '750000');

-- 사용자-계정 연결 기초 데이터
INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_NUMBER, RELATION_TYPE) VALUES
('USER001', '123-456-793', 'OWNER'),
('USER002', '123-456-789', 'OWNER'),
('USER003', '123-456-790', 'OWNER'),
('USER004', '123-456-791', 'OWNER'),
('USER005', '123-456-792', 'OWNER'),
('USER001', '123-456-789', 'JOINT'),
('USER002', '123-456-790', 'JOINT');

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

-- 계정 타입 코드
('CODE009', 'ACCOUNT_TYPE', 'SAVINGS', '저축예금', '저축 예금 계좌', 1, 'Y'),
('CODE010', 'ACCOUNT_TYPE', 'CHECKING', '당좌예금', '당좌 예금 계좌', 2, 'Y'),
('CODE011', 'ACCOUNT_TYPE', 'INVESTMENT', '투자계좌', '투자 계좌', 3, 'Y'),
('CODE012', 'ACCOUNT_TYPE', 'LOAN', '대출계좌', '대출 계좌', 4, 'Y'),

-- 계정 상태 코드
('CODE013', 'ACCOUNT_STATUS', 'ACTIVE', '활성', '활성 계좌', 1, 'Y'),
('CODE014', 'ACCOUNT_STATUS', 'INACTIVE', '비활성', '비활성 계좌', 2, 'Y'),
('CODE015', 'ACCOUNT_STATUS', 'FROZEN', '동결', '동결된 계좌', 3, 'Y'),
('CODE016', 'ACCOUNT_STATUS', 'CLOSED', '해지', '해지된 계좌', 4, 'Y'),

-- 통화 코드
('CODE017', 'CURRENCY', 'KRW', '원화', '대한민국 원화', 1, 'Y'),
('CODE018', 'CURRENCY', 'USD', '달러', '미국 달러', 2, 'Y'),
('CODE019', 'CURRENCY', 'EUR', '유로', '유럽 연합 유로', 3, 'Y'),
('CODE020', 'CURRENCY', 'JPY', '엔화', '일본 엔화', 4, 'Y'),

-- 거래 타입 코드
('CODE021', 'TRANSACTION_TYPE', 'DEPOSIT', '입금', '계좌 입금', 1, 'Y'),
('CODE022', 'TRANSACTION_TYPE', 'WITHDRAWAL', '출금', '계좌 출금', 2, 'Y'),
('CODE023', 'TRANSACTION_TYPE', 'TRANSFER', '이체', '계좌 이체', 3, 'Y'),
('CODE024', 'TRANSACTION_TYPE', 'PAYMENT', '결제', '결제 거래', 4, 'Y'),

-- 거래 상태 코드
('CODE025', 'TRANSACTION_STATUS', 'PENDING', '대기', '거래 대기', 1, 'Y'),
('CODE026', 'TRANSACTION_STATUS', 'COMPLETED', '완료', '거래 완료', 2, 'Y'),
('CODE027', 'TRANSACTION_STATUS', 'FAILED', '실패', '거래 실패', 3, 'Y'),
('CODE028', 'TRANSACTION_STATUS', 'CANCELLED', '취소', '거래 취소', 4, 'Y'),

-- 로그 레벨 코드
('CODE029', 'LOG_LEVEL', 'DEBUG', '디버그', '디버그 레벨', 1, 'Y'),
('CODE030', 'LOG_LEVEL', 'INFO', '정보', '정보 레벨', 2, 'Y'),
('CODE031', 'LOG_LEVEL', 'WARN', '경고', '경고 레벨', 3, 'Y'),
('CODE032', 'LOG_LEVEL', 'ERROR', '오류', '오류 레벨', 4, 'Y');

-- 역할 정보 기초 데이터
INSERT INTO ROLE (ROLE_ID, ROLE_NAME, ROLE_DESC, USE_YN) VALUES
('ROLE001', '시스템관리자', '시스템 전체 관리 권한', 'Y'),
('ROLE002', '부서관리자', '부서별 관리 권한', 'Y'),
('ROLE003', '일반사용자', '기본 사용자 권한', 'Y'),
('ROLE004', '게스트', '제한된 사용자 권한', 'Y');

-- 사용자 역할 연결 기초 데이터
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES
('USER001', 'ROLE001'),
('USER002', 'ROLE003'),
('USER003', 'ROLE003'),
('USER004', 'ROLE002'),
('USER005', 'ROLE003');

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
('MENU014', '설정관리', '/system/config', 'MENU004', 4, 'Y');

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
('ROLE003', 'MENU010', 'READ');

-- 거래 내역 기초 데이터
INSERT INTO TRANSACTION (TRANSACTION_ID, ACCOUNT_NUMBER, TRANSACTION_TYPE, AMOUNT, CURRENCY, DESCRIPTION, STATUS) VALUES
('TXN001', '123-456-789', 'DEPOSIT', 1000000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN002', '123-456-790', 'DEPOSIT', 500000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN003', '123-456-791', 'DEPOSIT', 2500000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN004', '123-456-792', 'DEPOSIT', 5000000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN005', '123-456-793', 'DEPOSIT', 750000.00, 'KRW', '초기 입금', 'COMPLETED'),
('TXN006', '123-456-789', 'WITHDRAWAL', 100000.00, 'KRW', 'ATM 출금', 'COMPLETED'),
('TXN007', '123-456-790', 'TRANSFER', 50000.00, 'KRW', '계좌 이체', 'COMPLETED'),
('TXN008', '123-456-791', 'DEPOSIT', 300000.00, 'KRW', '급여 입금', 'COMPLETED');

-- 시스템 설정 기초 데이터
INSERT INTO SYSTEM_CONFIG (CONFIG_ID, CONFIG_KEY, CONFIG_VALUE, CONFIG_DESC, USE_YN) VALUES
('CONFIG001', 'system.name', 'MBC System', '시스템 이름', 'Y'),
('CONFIG002', 'system.version', '1.0.0', '시스템 버전', 'Y'),
('CONFIG003', 'system.description', 'MBC 프로젝트 시스템', '시스템 설명', 'Y'),
('CONFIG004', 'app.debug', 'true', '디버그 모드', 'Y'),
('CONFIG005', 'app.data-access.type', 'jdbc', '데이터 접근 방식', 'Y'),
('CONFIG006', 'mbc.business.transaction-timeout', '30', '트랜잭션 타임아웃(초)', 'Y'),
('CONFIG007', 'mbc.business.max-retry-count', '3', '최대 재시도 횟수', 'Y'),
('CONFIG008', 'mbc.business.audit-logging', 'true', '감사 로깅 활성화', 'Y'),
('CONFIG009', 'mbc.cache.enabled', 'false', '캐시 활성화', 'Y'),
('CONFIG010', 'mbc.cache.ttl', '300', '캐시 TTL(초)', 'Y'),
('CONFIG011', 'mbc.cache.max-size', '1000', '캐시 최대 크기', 'Y');

-- 시스템 로그 기초 데이터
INSERT INTO SYSTEM_LOG (LOG_ID, USER_ID, LOG_TYPE, LOG_LEVEL, MESSAGE, IP_ADDRESS) VALUES
('LOG001', 'USER001', 'LOGIN', 'INFO', '관리자 로그인 성공', '127.0.0.1'),
('LOG002', 'USER001', 'SYSTEM', 'INFO', '시스템 초기화 완료', '127.0.0.1'),
('LOG003', 'USER002', 'LOGIN', 'INFO', '사용자 로그인 성공', '192.168.1.100'),
('LOG004', 'USER003', 'LOGIN', 'INFO', '사용자 로그인 성공', '192.168.1.101'),
('LOG005', 'USER001', 'USER', 'INFO', '새 사용자 등록: USER004', '127.0.0.1'),
('LOG006', 'USER001', 'ACCOUNT', 'INFO', '새 계정 등록: 123-456-792', '127.0.0.1'),
('LOG007', 'USER002', 'TRANSACTION', 'INFO', '거래 완료: TXN006', '192.168.1.100'),
('LOG008', 'USER003', 'TRANSACTION', 'INFO', '거래 완료: TXN007', '192.168.1.101'); 