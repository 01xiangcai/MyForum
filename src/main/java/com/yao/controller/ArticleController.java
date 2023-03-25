package com.yao.controller;


import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Article;
import com.yao.entity.dto.ArticleDto;
import com.yao.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.ResolveResult;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    //查看文章列表
    @ApiOperation("查看文章")
    @GetMapping("/articleList")
    public Result articlelist(Integer currentPage){
        return articleService.articleList(currentPage);
    }

    //根据文章id查看文章
    @ApiOperation("根据文章id查看文章")
    @GetMapping("/articleById/{id}")
    public Result articlebyId(@PathVariable("id") Long id){
        Article article = articleService.getById(id);
        if (article==null){
            return Result.fail(CustomizeResponseCode.ARTICLE_NOT_FOUND.getMessage());
        }
        return Result.succ(CustomizeResponseCode.ARTICLE_FOUND_SUCCESS.getMessage(),article);
    }

    //根据用户id查看文章
    @ApiOperation("根据用户id查看文章")
    @GetMapping("/articleByUserId/{id}")
    public Result articleByUserId(@PathVariable("id") Long id,
                                  Integer currentPage){
        return articleService.articleByUserId(id,currentPage);
    }

    //新增或修改文章
    @ApiOperation("新增或修改文章")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody @Validated ArticleDto articleDto, BindingResult result){
        if (result.hasErrors()){
            return Result.fail(CustomizeResponseCode.QUESTION_NOT_NULL.getMessage());
        }
        return articleService.saveOrUpdateArticle(articleDto);
    }



}
