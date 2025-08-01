package com.skax.eatool.mbb.dc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 테이블 컬럼 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableColumnInfoDDTO extends NewAbstractDTO {
    
    /**
     * 컬럼명
     */
    private String columnName;
    
    /**
     * 데이터 타입
     */
    private String dataType;
    
    /**
     * 컬럼 크기
     */
    private Integer columnSize;
    
    /**
     * NULL 허용 여부
     */
    private boolean nullable;
    
    /**
     * 기본값
     */
    private String defaultValue;
    
    /**
     * 컬럼 설명
     */
    private String remarks;
    
    /**
     * Primary Key 여부
     */
    private boolean primaryKey;
    
    /**
     * 컬럼 순서
     */
    private Integer ordinalPosition;
    
    /**
     * 소수점 자릿수
     */
    private Integer decimalDigits;
    
    /**
     * 자동 증가 여부
     */
    private boolean autoIncrement;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }
    
    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }
    
    public Integer getColumnSize() { return columnSize; }
    public void setColumnSize(Integer columnSize) { this.columnSize = columnSize; }
    
    public boolean isNullable() { return nullable; }
    public void setNullable(boolean nullable) { this.nullable = nullable; }
    
    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public boolean isPrimaryKey() { return primaryKey; }
    public void setPrimaryKey(boolean primaryKey) { this.primaryKey = primaryKey; }
    
    public Integer getOrdinalPosition() { return ordinalPosition; }
    public void setOrdinalPosition(Integer ordinalPosition) { this.ordinalPosition = ordinalPosition; }
    
    public Integer getDecimalDigits() { return decimalDigits; }
    public void setDecimalDigits(Integer decimalDigits) { this.decimalDigits = decimalDigits; }
    
    public boolean isAutoIncrement() { return autoIncrement; }
    public void setAutoIncrement(boolean autoIncrement) { this.autoIncrement = autoIncrement; }
} 