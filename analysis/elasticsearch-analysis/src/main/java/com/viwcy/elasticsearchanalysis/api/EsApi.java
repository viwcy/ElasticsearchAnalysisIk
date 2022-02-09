package com.viwcy.elasticsearchanalysis.api;

import com.viwcy.elasticsearchanalysis.entity.DataReport;
import com.viwcy.elasticsearchanalysis.param.SearchParam;
import com.viwcy.elasticsearchanalysis.repository.DataReportRepository;
import com.viwcy.elasticsearchanalysis.vo.BaseSearchVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/es")
@Slf4j
public class EsApi {

    @Autowired
    private DataReportRepository dataReportRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("/save")
    public String save(@RequestBody DataReport dataReport) {
        dataReportRepository.saveBatch(new ArrayList<DataReport>() {{
            add(dataReport);
        }});
        return "ok";
    }

    @PostMapping("/update")
    public String update(@RequestBody DataReport dataReport) {
        dataReportRepository.update(dataReport);
        return "ok";
    }

    @GetMapping("/query")
    public List<?> query(@RequestParam String name) {
        PageRequest pageable = PageRequest.ofSize(100);
        // 构建查询条件
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        filter.must(QueryBuilders.multiMatchQuery(
                name, "author", "content").fuzziness(Fuzziness.AUTO));

        searchQueryBuilder.withFilter(filter);
        // 分页信息
        searchQueryBuilder.withPageable(pageable);

        NativeSearchQuery query = searchQueryBuilder.build();
        SearchHits<DataReport> searchHits = elasticsearchRestTemplate.search(query, DataReport.class);
        log.info("搜索结果: {}\n{}", searchHits, searchHits.getSearchHits().toString());
        List<SearchHit<DataReport>> searchHits1 = searchHits.getSearchHits();
        return searchHits1.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @PostMapping("/page")
    public Map<String, Object> highlightPage(@RequestBody SearchParam param) {
        BaseSearchVO vo = new BaseSearchVO();
        ArrayList<String> fields = new ArrayList<String>() {{
            add("content");
        }};
        ArrayList<String> highlight = new ArrayList<String>() {{
            add("content");
        }};
        vo.setFields(fields);
        vo.setHighlightFields(highlight);
        Map<String, Object> search = dataReportRepository.highlightSearch(elasticsearchRestTemplate, param, vo, DataReport.class);
        return search;
    }
}
