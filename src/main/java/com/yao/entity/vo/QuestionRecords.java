package com.yao.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: QuestionRecords
 * @Description: TODO
 * @author: long
 * @date: 2023/3/20 16:23
 */
@Data
public class QuestionRecords {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 问题描述
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
     * 创建者id
     */
    private Long creator;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 观看数量
     */
    private Integer viewCount;

    /**
     * 标签
     */
    private String tag;

    private Integer deleted;


    private String creator_name;

    private String creator_avatar;

    private String creator_email;

    private Integer creator_status;

    private LocalDateTime creator_lastLogin;
}
