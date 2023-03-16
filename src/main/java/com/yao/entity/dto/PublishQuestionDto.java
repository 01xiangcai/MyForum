package com.yao.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @className: PublishQuestionDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/14 16:50
 */
@Data
public class PublishQuestionDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    @NotBlank
    private String title;

    /**
     * 问题描述
     */
    @NotBlank
    private String description;

    /**
     * 创建者id
     */
    @NotNull
    private Long creator;

    @NotBlank
    private String tag;

}
