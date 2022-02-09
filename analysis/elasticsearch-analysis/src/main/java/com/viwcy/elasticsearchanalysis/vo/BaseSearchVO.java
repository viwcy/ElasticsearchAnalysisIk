package com.viwcy.elasticsearchanalysis.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class BaseSearchVO extends PageVO implements Serializable {

    /**
     * 实体多字段匹配集合
     * ep:["title","content"]
     */
    private List<String> fields;

    /**
     * 排序字段
     * ep:[
     * {
     * "field":"id",
     * "order":"desc"
     * },
     * {
     * "field":"create_time",
     * "order":"asc"
     * }
     * ]
     */
    private Collection<OrderVO> orders;

    /**
     * 高亮字段
     * ep:["content"]
     */
    private Collection<String> highlightFields;
}
