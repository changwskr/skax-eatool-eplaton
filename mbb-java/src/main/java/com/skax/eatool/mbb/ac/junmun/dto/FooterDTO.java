package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 전문 종료부 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FooterDTO extends NewAbstractDTO {
    
    /**
     * 표준전문종료값 - 전문 종료를 나타내는 고정값
     */
    private String stdGramEndVal;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getStdGramEndVal() { return stdGramEndVal; }
    public void setStdGramEndVal(String stdGramEndVal) { this.stdGramEndVal = stdGramEndVal; }
} 