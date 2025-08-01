/**
 * (@)# TableDefinitionDDTO.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : 테이블 정의 DTO 클래스
 * 
 * 주요 기능:
 * - 테이블 정보 저장
 * - 컬럼 정보 관리
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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 테이블 정의서 DTO
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableDefinitionDDTO extends NewAbstractDTO {
    
    private String tableName;
    private String tableComment;
    private List<ColumnDefinition> columns;
    
    /**
     * 테이블 코멘트를 반환합니다.
     * 
     * @return 테이블 코멘트
     */
    public String getComment() {
        return tableComment;
    }
    
    /**
     * 컬럼 정의 내부 클래스
     */
    @Data
    public static class ColumnDefinition {
        private String name;
        private String type;
        private Integer length;
        private String defaultValue;
        private boolean pk;
        private boolean nullable;
        private String comment;
    }
} 