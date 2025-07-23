-- MBA (Master Business Application) Database Schema
-- 생성일: 2024-01-01
-- 설명: MBA 애플리케이션을 위한 기본 데이터베이스 스키마

-- 사용자 테이블
CREATE TABLE IF NOT EXISTS mba_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 사용자 프로필 테이블
CREATE TABLE IF NOT EXISTS mba_user_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    phone VARCHAR(20),
    department VARCHAR(100),
    position VARCHAR(100),
    avatar_url VARCHAR(255),
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES mba_users(id) ON DELETE CASCADE
);

-- 시스템 설정 테이블
CREATE TABLE IF NOT EXISTS mba_system_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT,
    description VARCHAR(255),
    category VARCHAR(50) NOT NULL DEFAULT 'GENERAL',
    is_encrypted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 로그 테이블
CREATE TABLE IF NOT EXISTS mba_audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(100) NOT NULL,
    resource_type VARCHAR(50),
    resource_id VARCHAR(100),
    details TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES mba_users(id) ON DELETE SET NULL
);

-- 메뉴 테이블
CREATE TABLE IF NOT EXISTS mba_menus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    name VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    url VARCHAR(255),
    icon VARCHAR(50),
    sort_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES mba_menus(id) ON DELETE CASCADE
);

-- 권한 테이블
CREATE TABLE IF NOT EXISTS mba_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 역할 테이블
CREATE TABLE IF NOT EXISTS mba_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 역할-권한 매핑 테이블
CREATE TABLE IF NOT EXISTS mba_role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES mba_roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES mba_permissions(id) ON DELETE CASCADE
);

-- 사용자-역할 매핑 테이블
CREATE TABLE IF NOT EXISTS mba_user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES mba_users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES mba_roles(id) ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX idx_mba_users_username ON mba_users(username);
CREATE INDEX idx_mba_users_email ON mba_users(email);
CREATE INDEX idx_mba_users_status ON mba_users(status);
CREATE INDEX idx_mba_audit_logs_user_id ON mba_audit_logs(user_id);
CREATE INDEX idx_mba_audit_logs_created_at ON mba_audit_logs(created_at);
CREATE INDEX idx_mba_audit_logs_action ON mba_audit_logs(action);
CREATE INDEX idx_mba_system_config_key ON mba_system_config(config_key);
CREATE INDEX idx_mba_system_config_category ON mba_system_config(category);
CREATE INDEX idx_mba_menus_parent_id ON mba_menus(parent_id);
CREATE INDEX idx_mba_menus_sort_order ON mba_menus(sort_order); 