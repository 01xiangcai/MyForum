package com.yao.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: ArticleVo
 * @Description: TODO
 * @author: long
 * @date: 2023/3/25 16:33
 */
@Data
public class ArticleVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private List<ArticleRecords> articleRecords;

    //数据总数
    private Long total;

    private Long  pageSize;

    private Long currentPage;


}
