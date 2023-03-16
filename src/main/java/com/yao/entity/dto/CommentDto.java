package com.yao.entity.dto;

import lombok.Data;

import javax.annotation.security.DenyAll;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: CommentDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/15 16:29
 */
@Data
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    //父类类型
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
