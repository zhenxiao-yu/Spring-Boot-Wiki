package com.zhenxiao.wiki.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PageReq {
    //page number
    @NotNull(message ="[page number] can't be null")
    private int page;

    //page size
    @NotNull(message ="[page size] can't be null")
    @Max(value = 1000,message = "[page size] must <= 1000")
    private int size;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageReq{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}