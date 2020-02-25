package com.leyou.search.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020/2/19.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.pojo
 */

public class SearchRequest {

    private String key;

    private Integer page;

    private Integer pageRow; //当前页多少

    private String sortBy;

    private Boolean descending;

    private Map<String,Object> filter =new HashMap<>();

    private static final int DEFAULT_PAGE = 1;

    private static final int DEFAULT_PAGEROW = 20;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null)
        {
            page = DEFAULT_PAGE;
        }

        return Math.max(page,DEFAULT_PAGE);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageRow() {
        if (pageRow == null)
        {
            pageRow = DEFAULT_PAGEROW;
        }
        return pageRow;
    }

    public void setPageRow(Integer pageRow) {
        this.pageRow = pageRow;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }
}
