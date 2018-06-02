package com.axway.univmgmt.entity;

import org.springframework.data.domain.Page;

import java.util.LinkedList;
import java.util.List;

public class PageData<T> {

    private long total;
    private long pageSize;
    private long pageNumber;
    private List<T> data;

    public PageData() {
    }

    public PageData( Page page ) {
        total = page.getTotalElements();
        pageSize = page.getSize();
        pageNumber = page.getNumber();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal( long total ) {
        this.total = total;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize( long pageSize ) {
        this.pageSize = pageSize;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber( long pageNumber ) {
        this.pageNumber = pageNumber;
    }

    public List<T> getData() {
        return data;
    }

    public void setData( List<T> data ) {
        this.data = data;
    }

    public void addData( T element ) {
        if( data == null ) {
            data = new LinkedList<>();
        }
        data.add( element );
    }
}
