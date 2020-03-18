package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/12/23.
 * Version: 1.0
 * Describe:项目列表过滤条件实体
 */
public class FilterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * filterName : 贷款类型
     * filterKey : 1001
     * filterChild : false
     * filterItems : [{"key":"","value":"不限"},{"key":"40047001","value":"小微助业贷款"},{"key":"40047002","value":"二手房贷款"},{"key":"40047003","value":"楼盘整体评估"},{"key":"40047004","value":"综合消费类贷款"}]
     */

    private String filterName;
    private String filterKey;
    private boolean filterChild;
    private List<FilterItemEntity> filterItems;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public boolean isFilterChild() {
        return filterChild;
    }

    public void setFilterChild(boolean filterChild) {
        this.filterChild = filterChild;
    }

    public List<FilterItemEntity> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<FilterItemEntity> filterItems) {
        this.filterItems = filterItems;
    }
}
