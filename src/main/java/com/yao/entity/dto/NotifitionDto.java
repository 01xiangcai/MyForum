package com.yao.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: NotifitionDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/31 16:37
 */
@Data
public class NotifitionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建通知者id
     */
    private Long notifier;

    /**
     * 通知对象id
     */
    private Long receiver;

    /**
     * 通知类型，1为文章，2为问题，11为文章评论，22为问题评论
     */
    private Integer type;

    /**
     * 被回复的文章/问题/评论id
     */
    private Long outerid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;



}
