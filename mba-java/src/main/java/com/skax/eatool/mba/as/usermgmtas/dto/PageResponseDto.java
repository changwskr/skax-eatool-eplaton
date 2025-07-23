package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 페이지 응답 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseDto<T> {
    
    @JsonProperty("content")
    private List<T> content;
    
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("size")
    private int size;
    
    @JsonProperty("totalElements")
    private Long totalElements;
    
    @JsonProperty("totalPages")
    private int totalPages;
    
    @JsonProperty("hasNext")
    private boolean hasNext;
    
    @JsonProperty("hasPrevious")
    private boolean hasPrevious;
    
    @JsonProperty("isFirst")
    private boolean isFirst;
    
    @JsonProperty("isLast")
    private boolean isLast;
    
    // 기본 생성자
    public PageResponseDto() {}
    
    // 생성자
    public PageResponseDto(List<T> content, int page, int size, Long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = calculateTotalPages(totalElements, size);
        this.hasNext = page < totalPages - 1;
        this.hasPrevious = page > 0;
        this.isFirst = page == 0;
        this.isLast = page >= totalPages - 1;
    }
    
    // 정적 팩토리 메서드
    public static <T> PageResponseDto<T> of(List<T> content, int page, int size, Long totalElements) {
        return new PageResponseDto<>(content, page, size, totalElements);
    }
    
    public static <T> PageResponseDto<T> empty(int page, int size) {
        return new PageResponseDto<>(List.of(), page, size, 0L);
    }
    
    // Getter와 Setter
    public List<T> getContent() {
        return content;
    }
    
    public void setContent(List<T> content) {
        this.content = content;
    }
    
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
    
    public Long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = calculateTotalPages(totalElements, size);
        updateNavigationFlags();
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        updateNavigationFlags();
    }
    
    public boolean isHasNext() {
        return hasNext;
    }
    
    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
    
    public boolean isHasPrevious() {
        return hasPrevious;
    }
    
    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
    
    public boolean isFirst() {
        return isFirst;
    }
    
    public void setIsFirst(boolean first) {
        isFirst = first;
    }
    
    public boolean isLast() {
        return isLast;
    }
    
    public void setIsLast(boolean last) {
        isLast = last;
    }
    
    // 비즈니스 메서드
    private int calculateTotalPages(Long totalElements, int size) {
        if (totalElements == null || totalElements <= 0 || size <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / size);
    }
    
    private void updateNavigationFlags() {
        this.hasNext = page < totalPages - 1;
        this.hasPrevious = page > 0;
        this.isFirst = page == 0;
        this.isLast = page >= totalPages - 1;
    }
    
    public int getNumberOfElements() {
        return content != null ? content.size() : 0;
    }
    
    public boolean isEmpty() {
        return content == null || content.isEmpty();
    }
    
    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }
    
    public int getNextPage() {
        return hasNext ? page + 1 : page;
    }
    
    public int getPreviousPage() {
        return hasPrevious ? page - 1 : page;
    }
    
    @Override
    public String toString() {
        return "PageResponseDto{" +
                "content=" + (content != null ? content.size() : 0) + " items" +
                ", page=" + page +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", hasNext=" + hasNext +
                ", hasPrevious=" + hasPrevious +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                '}';
    }
} 