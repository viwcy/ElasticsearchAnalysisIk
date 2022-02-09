package com.viwcy.elasticsearchanalysis.repository;

import com.viwcy.elasticsearchanalysis.entity.DataReport;
import com.viwcy.elasticsearchanalysis.param.SearchParam;
import com.viwcy.elasticsearchanalysis.vo.BaseSearchVO;
import com.viwcy.elasticsearchanalysis.vo.OrderVO;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@NoRepositoryBean
@Service
public interface BaseRepository<T, ID> extends ElasticsearchRepository<T, ID> {

    Logger log = LoggerFactory.getLogger(BaseRepository.class);

    String PRE_TAG = "<em>";

    String POST_TAG = "</em>";

    /**
     * 批量保存
     */
    default void saveBatch(@Nullable Collection<T> t) {
        try {
            t.stream().forEach(obj -> this.save(obj));
        } catch (Exception e) {
            log.error("批量保存失败，cause = ", e);
        }
    }

    /**
     * 更新
     */
    default void update(@Nullable T t) {
        try {
            this.save(t);
        } catch (Exception e) {
            log.error("更新失败，cause = ", e);
        }
    }

    /**
     * 批量更新
     */
    default void updateBatch(@Nullable Collection<T> t) {
        try {
            t.stream().forEach(obj -> this.save(obj));
        } catch (Exception e) {
            log.error("批量更新失败，cause = ", e);
        }
    }

    /**
     * 多条件高亮分页查询
     */
    default Map<String, Object> highlightSearch(@Nullable ElasticsearchRestTemplate elasticsearchRestTemplate, SearchParam param, BaseSearchVO vo, Class<T> clazz) {

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        //多条件查询
        List<String> fields = vo.getFields();
        fields.forEach(field -> builder.withQuery(QueryBuilders.multiMatchQuery(param.getKeyword(), field)));

        //排序
        Collection<OrderVO> orders = vo.getOrders();
        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(order -> builder.withSort(SortBuilders.fieldSort(order.getField()).order(order.getOrder())));
        }

        //分页
        if (param.getIsPage().equals("YES")) {
            builder.withPageable(PageRequest.of(param.getPage() - 1, param.getSize()));
        }

        //高亮
        Collection<String> highlightFields = vo.getHighlightFields();
        if (!CollectionUtils.isEmpty(highlightFields)) {
            highlightFields.forEach(highlightField -> {
                builder.withHighlightFields(
                        new HighlightBuilder.Field(highlightField).preTags(PRE_TAG).postTags(POST_TAG)
                );
            });
        }

        NativeSearchQuery searchQuery = builder.build();

        SearchHits<T> search = elasticsearchRestTemplate.search(searchQuery, clazz);
        // 得到查询结果返回的内容
        List<SearchHit<T>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<T> result = new ArrayList<>();
        // 遍历返回的内容进行处理
        for (SearchHit<T> searchHit : searchHits) {
            T t = searchHit.getContent();
            // 高亮的内容
            Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            if (!CollectionUtils.isEmpty(highlightFields)) {
                highlightFields.forEach(highlightField -> {
                    try {
                        Field declaredField = t.getClass().getDeclaredField(highlightField);
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(t, highLightFields.get(highlightField).get(0));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
            }
            result.add(searchHit.getContent());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("total", (int) search.getTotalHits());
        return map;
    }
}
