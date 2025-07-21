package com.skax.eatool.ksa.infra.po;

/**
 * NewKBData implementation
 */
public class NewKBData {

    private NewGenericDto inputGenericDto;
    private NewGenericDto outputGenericDto;

    public NewKBData() {
        this.inputGenericDto = new NewGenericDto();
        this.outputGenericDto = new NewGenericDto();
    }

    public String toString() {
        return "NewKBData{input=" + inputGenericDto + ", output=" + outputGenericDto + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NewKBData that = (NewKBData) obj;
        return inputGenericDto.equals(that.inputGenericDto) && 
               outputGenericDto.equals(that.outputGenericDto);
    }

    public int hashCode() {
        return inputGenericDto.hashCode() * 31 + outputGenericDto.hashCode();
    }

    public NewGenericDto getInputGenericDto() {
        return inputGenericDto;
    }

    public NewGenericDto getOutputGenericDto() {
        return outputGenericDto;
    }
}