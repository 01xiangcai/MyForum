package com.yao.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: ArticleDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/25 17:12
 */
@Data
public class ArticleDto implements Serializable {

    private static final long serialVersionUID = 1L;



    protected Long id;

    /**
     * 发布人
     */
    private Long creator;

    /**
     * 文章标题
     */
    @NotBlank
    private String title;

    /**
     * 文章内容
     */
    @NotBlank
    private String description;

    @NotBlank
    private String tag;


}
