package com.viwcy.elasticsearchanalysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2020/9/1 16:29
 * @Author Fuqiang
 * <p>
 *
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("data_report")
@Document(indexName = "domain")
public class DataReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    @TableField(value = "sync_es")
    private Integer syncEs;
}
