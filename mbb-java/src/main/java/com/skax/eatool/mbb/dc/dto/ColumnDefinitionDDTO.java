/**
 * (@)# ColumnDefinitionDDTO.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : 컬럼 정의 DTO 클래스
 * 
 * 주요 기능:
 * - 컬럼 정보 저장
 * - 컬럼 속성 관리
 * 
 * 변경이력 :
 *   <ul>
 *     <li>2024-01-01 :: SKAX Team :: 신규작성</li>
 *   </ul>
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
package com.skax.eatool.mbb.dc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 컬럼 정의 DTO 클래스
 * 
 * AI 코딩 워크플로우에서 컬럼 정보를 처리하기 위한 DTO입니다.
 * 컬럼명, 타입, 제약조건 등을 포함합니다.
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
public class ColumnDefinitionDDTO extends NewAbstractDTO {
    
    /** 컬럼명 */
    @JsonProperty("name")
    private String name_;
    
    /** 컬럼 타입 */
    @JsonProperty("type")
    private String type_;
    
    /** 기본키 여부 */
    @JsonProperty("pk")
    private Boolean pk_;
    
    /** NULL 허용 여부 */
    @JsonProperty("nullable")
    private Boolean nullable_;
    
    /** 컬럼 설명 */
    @JsonProperty("comment")
    private String comment_;
    
    /** 기본값 */
    @JsonProperty("default")
    private String defaultValue_;
    
    /**
     * 기본 생성자
     */
    public ColumnDefinitionDDTO() {
        super();
    }
    
    /**
     * 컬럼명을 반환한다.
     * 
     * @return 컬럼명
     */
    public String getName() {
        return name_;
    }
    
    /**
     * 컬럼명을 설정한다.
     * 
     * @param name 컬럼명
     */
    public void setName(String name) {
        this.name_ = name;
    }
    
    /**
     * 컬럼 타입을 반환한다.
     * 
     * @return 컬럼 타입
     */
    public String getType() {
        return type_;
    }
    
    /**
     * 컬럼 타입을 설정한다.
     * 
     * @param type 컬럼 타입
     */
    public void setType(String type) {
        this.type_ = type;
    }
    
    /**
     * 기본키 여부를 반환한다.
     * 
     * @return 기본키 여부
     */
    public Boolean getPk() {
        return pk_;
    }
    
    /**
     * 기본키 여부를 설정한다.
     * 
     * @param pk 기본키 여부
     */
    public void setPk(Boolean pk) {
        this.pk_ = pk;
    }
    
    /**
     * NULL 허용 여부를 반환한다.
     * 
     * @return NULL 허용 여부
     */
    public Boolean getNullable() {
        return nullable_;
    }
    
    /**
     * NULL 허용 여부를 설정한다.
     * 
     * @param nullable NULL 허용 여부
     */
    public void setNullable(Boolean nullable) {
        this.nullable_ = nullable;
    }
    
    /**
     * 컬럼 설명을 반환한다.
     * 
     * @return 컬럼 설명
     */
    public String getComment() {
        return comment_;
    }
    
    /**
     * 컬럼 설명을 설정한다.
     * 
     * @param comment 컬럼 설명
     */
    public void setComment(String comment) {
        this.comment_ = comment;
    }
    
    /**
     * 기본값을 반환한다.
     * 
     * @return 기본값
     */
    public String getDefaultValue() {
        return defaultValue_;
    }
    
    /**
     * 기본값을 설정한다.
     * 
     * @param defaultValue 기본값
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue_ = defaultValue;
    }
} 