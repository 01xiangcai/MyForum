package com.yao.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: ArticleRecords
 * @Description: TODO
 * @author: long
 * @date: 2023/3/25 16:32
 */
@Data
public class ArticleRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 发布人
     */
    private Long creator;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    /**
     * 喜欢数量
     */
    private Integer likeCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 看过数量
     */
    private Integer viewCount;

    /**
     * 标签
     */
    private Long tag;

    private Integer deleted;


    private String creator_name;

    private String creator_avatar;

    private String creator_email;

    private Integer creator_status;

    private LocalDateTime creator_lastLogin;
}
