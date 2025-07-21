package com.skax.eatool.mbc.fc.foundation.entity;

import java.time.LocalDateTime;

/**
 * 기본 엔티티 클래스
 * 
 * 프로그램명: BaseEntity.java
 * 설명: 모든 엔티티가 상속받을 기본 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 생성일시 설정
 * - 수정일시 설정
 * - 기본 필드 관리
 * 
 * @version 1.0
 */
public abstract class BaseEntity {

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;

    // Getters and Setters
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}