package com.yao.service.impl;

import com.yao.common.CommentTypeEnum;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Comment;
import com.yao.entity.dto.CommentDto;
import com.yao.mapper.CommentMapper;
import com.yao.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public Result createComment(CommentDto commentDto) {

        //评论类型不存在
        if (!CommentTypeEnum.exist(commentDto.getType())){
            return Result.fail(CustomizeResponseCode.COMMENT_INSERT_FAIL.getMessage());
        }

        Comment comment = new Comment();
        LocalDateTime now = LocalDateTime.now();

        BeanUtils.copyProperties(commentDto,comment);
        comment.setGmtCreate(now);

        int rows = commentMapper.insert(comment);

        if (rows==0){
            return Result.fail(CustomizeResponseCode.COMMENT_INSERT_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.COMMENT_INSERT_SUCCESS.getMessage());
    }
}
