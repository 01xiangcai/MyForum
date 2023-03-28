package com.yao.controller;


import com.yao.common.CommentTypeEnum;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.dto.ArticleCommentDto;
import com.yao.entity.dto.CommentDto;
import com.yao.service.ArticleCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author long
 * @since 2023-03-26
 */
@RestController
@RequestMapping("/article-comment")
public class ArticleCommentController {

    @Autowired
    ArticleCommentService articleCommentService;

    @ApiOperation("增加评论")
    @PostMapping("/insertComment")
    public Result createComment(@RequestBody @Validated ArticleCommentDto articleCommentDto, BindingResult result){
        if (result.hasErrors()){
            return Result.fail(CustomizeResponseCode.COMMENT_IS_NULL.getMessage());
        }
        return articleCommentService.createComment(articleCommentDto);
    }

    @ApiOperation("查询评论")
    @GetMapping("/commentList")
    public Result commentList(@RequestParam("parentId") Long parentId,
                              @RequestParam("type") Integer type){
        if (!CommentTypeEnum.exist(type)){
            return Result.fail(CustomizeResponseCode.COMMENT_TYPE_ERROE.getMessage());
        }
        return articleCommentService.commentList(parentId,type);
    }


}
