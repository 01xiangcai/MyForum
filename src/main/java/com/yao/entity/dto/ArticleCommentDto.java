package com.yao.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @className: ArticleCommentDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/26 15:58
 */
@Data
public class ArticleCommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    //父类id
    private Long parentId;

    /**
     * 父类类型
     */
    @NotNull
    private Integer type;

    /**
     * 评论人id
     */
    @NotNull
    private Long commentator;


    /**
     * 评论内容
     */
    @NotBlank
    private String content;
}
