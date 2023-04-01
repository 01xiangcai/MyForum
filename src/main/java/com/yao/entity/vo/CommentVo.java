package com.yao.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yao.entity.Comment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: CommentVo
 * @Description: TODO
 * @author: long
 * @date: 2023/3/22 14:52
 */
@Data
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    //评论id
    private Long id;

    /**
     * 父类id
     */
    private Long parentId;

    /**
     * 父类类型
     */
    private Integer type;

    /**
     * 评论人id
     */
    private Long commentator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    /**
     * 喜欢数量
     */
    private Long likeCount;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 二级评论数量
     */
    private Integer commentCount;

    private String username;

    private String avatar;

}
