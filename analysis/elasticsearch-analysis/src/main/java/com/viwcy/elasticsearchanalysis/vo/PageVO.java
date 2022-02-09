package com.viwcy.elasticsearchanalysis.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class PageVO {

    @JsonAlias(value = {"current"})
    private int page = 1;
    @JsonAlias(value = {"pageSize"})
    private int size = 10;
}
