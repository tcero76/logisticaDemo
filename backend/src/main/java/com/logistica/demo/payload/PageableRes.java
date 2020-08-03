package com.logistica.demo.payload;

import com.logistica.demo.model.Inventario;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PageableRes<T> {

    public PageableRes(Integer totalPage, Integer page, List<T> list) {
        this.totalPage = totalPage;
        this.page = page;
        this.list = list;
    }

    private Integer totalPage;

    private Integer page;

    private List<T> list;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
