package com.yao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author long
 * @since 2023-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Notifition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 是否已读，0为未读，1为已读
     */
    private Integer readed;

    /**
     * 是否删除，0为未删除，1为删除

     */
    private Integer deleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;


}
