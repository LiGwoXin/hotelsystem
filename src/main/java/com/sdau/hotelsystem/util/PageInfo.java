package com.sdau.hotelsystem.util;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {
    private int code=0;
    private String msg;
    private Long count;
    private List<T> data;

    /**
     * 只有总条数和分页数据的构造方法
     * @param count 总条数
     * @param data 分页数据
     */
    public PageInfo(Long count, List<T> data) {
        this.count = count;
        this.data = data;
    }
}
