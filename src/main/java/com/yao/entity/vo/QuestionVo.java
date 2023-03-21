package com.yao.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: QuestionVo
 * @Description: TODO
 * @author: long
 * @date: 2023/3/15 14:21
 */
@Data
public class QuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;


    List<QuestionRecords> questionRecords;

    //数据总数
    private Long total;

    private Long  pageSize;

    private Long currentPage;


}
