-- MBA (Master Business Application) Initial Data
-- 생성일: 2024-01-01
-- 설명: MBA 애플리케이션을 위한 기본 데이터

-- 기본 역할 데이터
INSERT INTO mba_roles (name, display_name, description) VALUES
('ADMIN', '관리자', '시스템 전체 관리 권한을 가진 역할'),
('USER', '일반 사용자', '기본 사용자 권한을 가진 역할'),
('MANAGER', '매니저', '부서 관리 권한을 가진 역할')
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

-- 기본 권한 데이터
INSERT INTO mba_permissions (name, display_name, description) VALUES
('USER_READ', '사용자 조회', '사용자 정보 조회 권한'),
('USER_CREATE', '사용자 생성', '새 사용자 생성 권한'),
('USER_UPDATE', '사용자 수정', '사용자 정보 수정 권한'),
('USER_DELETE', '사용자 삭제', '사용자 삭제 권한'),
('SYSTEM_CONFIG_READ', '시스템 설정 조회', '시스템 설정 조회 권한'),
('SYSTEM_CONFIG_UPDATE', '시스템 설정 수정', '시스템 설정 수정 권한'),
('AUDIT_LOG_READ', '감사 로그 조회', '감사 로그 조회 권한'),
('MENU_MANAGE', '메뉴 관리', '메뉴 관리 권한')
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

-- 역할-권한 매핑 데이터
INSERT INTO mba_role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM mba_roles r, mba_permissions p 
WHERE r.name = 'ADMIN' AND p.name IN (
    'USER_READ', 'USER_CREATE', 'USER_UPDATE', 'USER_DELETE',
    'SYSTEM_CONFIG_READ', 'SYSTEM_CONFIG_UPDATE', 'AUDIT_LOG_READ', 'MENU_MANAGE'
)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

INSERT INTO mba_role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM mba_roles r, mba_permissions p 
WHERE r.name = 'MANAGER' AND p.name IN (
    'USER_READ', 'USER_CREATE', 'USER_UPDATE',
    'SYSTEM_CONFIG_READ', 'AUDIT_LOG_READ'
)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

INSERT INTO mba_role_permissions (role_id, permission_id) 
SELECT r.id, p.id FROM mba_roles r, mba_permissions p 
WHERE r.name = 'USER' AND p.name IN (
    'USER_READ'
)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

-- 기본 관리자 사용자 생성 (비밀번호: admin123)
INSERT INTO mba_users (username, password, email, full_name, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'admin@skax.com', '시스템 관리자', 'ADMIN', 'ACTIVE'),
('manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'manager@skax.com', '매니저', 'MANAGER', 'ACTIVE'),
('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'user@skax.com', '일반 사용자', 'USER', 'ACTIVE')
ON DUPLICATE KEY UPDATE email = VALUES(email);

-- 사용자-역할 매핑 데이터
INSERT INTO mba_user_roles (user_id, role_id) 
SELECT u.id, r.id FROM mba_users u, mba_roles r 
WHERE u.username = 'admin' AND r.name = 'ADMIN'
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

INSERT INTO mba_user_roles (user_id, role_id) 
SELECT u.id, r.id FROM mba_users u, mba_roles r 
WHERE u.username = 'manager' AND r.name = 'MANAGER'
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

INSERT INTO mba_user_roles (user_id, role_id) 
SELECT u.id, r.id FROM mba_users u, mba_roles r 
WHERE u.username = 'user' AND r.name = 'USER'
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

-- 기본 메뉴 데이터
INSERT INTO mba_menus (parent_id, name, display_name, url, icon, sort_order) VALUES
(NULL, 'dashboard', '대시보드', '/mba/dashboard', 'dashboard', 1),
(NULL, 'user-management', '사용자 관리', '/mba/users', 'people', 2),
(NULL, 'system', '시스템 관리', '/mba/system', 'settings', 3),
(NULL, 'reports', '보고서', '/mba/reports', 'assessment', 4)
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

-- 하위 메뉴 데이터
INSERT INTO mba_menus (parent_id, name, display_name, url, icon, sort_order) 
SELECT m.id, 'user-list', '사용자 목록', '/mba/users/list', 'list', 1
FROM mba_menus m WHERE m.name = 'user-management'
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

INSERT INTO mba_menus (parent_id, name, display_name, url, icon, sort_order) 
SELECT m.id, 'user-create', '사용자 생성', '/mba/users/create', 'add', 2
FROM mba_menus m WHERE m.name = 'user-management'
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

INSERT INTO mba_menus (parent_id, name, display_name, url, icon, sort_order) 
SELECT m.id, 'system-config', '시스템 설정', '/mba/system/config', 'settings', 1
FROM mba_menus m WHERE m.name = 'system'
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

INSERT INTO mba_menus (parent_id, name, display_name, url, icon, sort_order) 
SELECT m.id, 'audit-logs', '감사 로그', '/mba/system/logs', 'history', 2
FROM mba_menus m WHERE m.name = 'system'
ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);

-- 기본 시스템 설정 데이터
INSERT INTO mba_system_config (config_key, config_value, description, category) VALUES
('app.name', 'MBA - Master Business Application', '애플리케이션 이름', 'GENERAL'),
('app.version', '1.0.0', '애플리케이션 버전', 'GENERAL'),
('app.description', '마스터 비즈니스 애플리케이션', '애플리케이션 설명', 'GENERAL'),
('security.session.timeout', '3600', '세션 타임아웃 (초)', 'SECURITY'),
('security.password.min.length', '8', '최소 비밀번호 길이', 'SECURITY'),
('security.login.max.attempts', '5', '최대 로그인 시도 횟수', 'SECURITY'),
('logging.level', 'INFO', '로그 레벨', 'SYSTEM'),
('logging.retention.days', '30', '로그 보관 기간 (일)', 'SYSTEM'),
('email.enabled', 'false', '이메일 기능 활성화 여부', 'EMAIL'),
('email.smtp.host', 'localhost', 'SMTP 서버 호스트', 'EMAIL'),
('email.smtp.port', '587', 'SMTP 서버 포트', 'EMAIL')
ON DUPLICATE KEY UPDATE config_value = VALUES(config_value);

-- 기본 감사 로그 데이터
INSERT INTO mba_audit_logs (user_id, action, resource_type, resource_id, details, ip_address) 
SELECT u.id, 'SYSTEM_STARTUP', 'SYSTEM', 'STARTUP', 'MBA 애플리케이션 시작', '127.0.0.1'
FROM mba_users u WHERE u.username = 'admin'
ON DUPLICATE KEY UPDATE details = VALUES(details); 