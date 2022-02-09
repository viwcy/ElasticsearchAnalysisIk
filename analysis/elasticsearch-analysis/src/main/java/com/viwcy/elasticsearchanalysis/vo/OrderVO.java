package com.viwcy.elasticsearchanalysis.vo;

import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class OrderVO {

    private String field;
    private SortOrder order = SortOrder.DESC;
}
