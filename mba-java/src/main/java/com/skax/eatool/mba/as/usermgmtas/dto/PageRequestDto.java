package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 페이지 요청 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageRequestDto {
    
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("size")
    private int size;
    
    @JsonProperty("keyword")
    private String keyword;
    
    @JsonProperty("searchField")
    private String searchField;
    
    @JsonProperty("sortBy")
    private String sortBy;
    
    @JsonProperty("sortDirection")
    private String sortDirection;
    
    // 기본 생성자
    public PageRequestDto() {
        this.page = 0;
        this.size = 10;
        this.sortDirection = "DESC";
    }
    
    // 생성자 (페이지, 크기)
    public PageRequestDto(int page, int size) {
        this.page = page;
        this.size = size;
        this.sortDirection = "DESC";
    }
    
    // 생성자 (페이지, 크기, 키워드, 검색필드)
    public PageRequestDto(int page, int size, String keyword, String searchField) {
        this.page = page;
        this.size = size;
        this.keyword = keyword;
        this.searchField = searchField;
        this.sortDirection = "DESC";
    }
    
    // 정적 팩토리 메서드 - 검색용
    public static PageRequestDto forSearch(int page, int size, String keyword, String searchField) {
        return new PageRequestDto(page, size, keyword, searchField);
    }
    
    // 정적 팩토리 메서드 - 기본값
    public static PageRequestDto ofDefault() {
        return new PageRequestDto(0, 10);
    }
    
    // Getter와 Setter
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getSearchField() {
        return searchField;
    }
    
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
    
    // 비즈니스 메서드
    public int getOffset() {
        return page * size;
    }
    
    public boolean hasKeyword() {
        return keyword != null && !keyword.trim().isEmpty();
    }
    
    public boolean hasSearchField() {
        return searchField != null && !searchField.trim().isEmpty();
    }
    
    public boolean isAscending() {
        return "ASC".equalsIgnoreCase(sortDirection);
    }
    
    public boolean isDescending() {
        return "DESC".equalsIgnoreCase(sortDirection);
    }
    
    @Override
    public String toString() {
        return "PageRequestDto{" +
                "page=" + page +
                ", size=" + size +
                ", keyword='" + keyword + '\'' +
                ", searchField='" + searchField + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                '}';
    }
} 