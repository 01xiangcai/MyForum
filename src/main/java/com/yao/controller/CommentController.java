package com.yao.controller;


import com.yao.common.CommentTypeEnum;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.dto.CommentDto;
import com.yao.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("查询评论")
    @GetMapping("/commentList")
    public Result commentList(@RequestParam("parentId") Long parentId,
                              @RequestParam("type") Integer type){
        if (!CommentTypeEnum.exist(type)){
            return Result.fail(CustomizeResponseCode.COMMENT_TYPE_ERROE.getMessage());
        }
        return commentService.commentList(parentId,type);
    }



}
