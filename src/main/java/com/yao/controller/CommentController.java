package com.yao.controller;


import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.dto.CommentDto;
import com.yao.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  评论控制类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Api(tags = "评论管理")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation("增加评论")
    @PostMapping("/insertComment")
    public Result createComment(@RequestBody @Validated CommentDto commentDto, BindingResult result){
        if (result.hasErrors()){
            return Result.fail(CustomizeResponseCode.COMMENT_IS_NULL.getMessage());
        }
        return commentService.createComment(commentDto);
    }



}
