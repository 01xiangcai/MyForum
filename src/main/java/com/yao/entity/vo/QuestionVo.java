package com.yao.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: QuestionVo
 * @Description: TODO
 * @author: long
 * @date: 2023/3/15 14:21
 */
@Data
public class QuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
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
