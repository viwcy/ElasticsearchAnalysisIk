package com.viwcy.elasticsearchanalysis.param;

import com.viwcy.elasticsearchanalysis.vo.PageVO;
import lombok.Data;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class SearchParam extends PageVO {

    private String keyword;

    private String isPage = "NO";
}
