package com.example.mobile_store.dto;

public class PaginationDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 3;

    // Getters và Setters
    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
