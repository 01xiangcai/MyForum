package com.yao.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: NotificationVo
 * @Description: 返回给前端的通知传输模型
 * @author: long
 * @date: 2023/4/1 19:19
 */
@Data
public class NotificationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //创建通知的人的名字
    private String notifierName;

    /**
     * 通知对象id
     */
    private Long receiver;

    //通知对象的名字
    private String receiverName;

    /**
     * 被回复的文章/问题/评论id
     */
    private Long outerid;

    //被回复的文章、问题、评论的标题或具体内容
    private String outerName;

    //类型名字
    private String typeName;

    //通知时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;



}
