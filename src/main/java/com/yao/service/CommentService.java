package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.entity.dto.CommentDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
public interface CommentService extends IService<Comment> {

    Result createComment(CommentDto commentDto);
}
