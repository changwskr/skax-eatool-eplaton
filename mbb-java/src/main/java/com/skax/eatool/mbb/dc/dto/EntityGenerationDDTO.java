package com.skax.eatool.mbb.dc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Entity 생성 결과 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EntityGenerationDDTO extends NewAbstractDTO {
    
    /**
     * 테이블명
     */
    private String tableName;
    
    /**
     * 컬럼 정보 리스트
     */
    private List<TableColumnInfoDDTO> columns;
    
    /**
     * 생성된 Entity 코드
     */
    private String entityCode;
    
    /**
     * 파일 경로
     */
    private String filePath;
    
    /**
     * 성공 여부
     */
    private boolean success;
    
    /**
     * 결과 메시지
     */
    private String message;
    
    /**
     * 생성된 클래스명
     */
    private String className;
    
    /**
     * 패키지명
     */
    private String packageName;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    public List<TableColumnInfoDDTO> getColumns() { return columns; }
    public void setColumns(List<TableColumnInfoDDTO> columns) { this.columns = columns; }
    
    public String getEntityCode() { return entityCode; }
    public void setEntityCode(String entityCode) { this.entityCode = entityCode; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
} 