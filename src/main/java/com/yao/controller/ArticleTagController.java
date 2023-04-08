package com.yao.controller;


import com.yao.common.Result;
import com.yao.service.ArticleService;
import com.yao.service.ArticleTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author long
 * @since 2023-04-08
 */
@Api(tags = "标签管理")
@RestController
@RequestMapping("/article-tag")
public class ArticleTagController {

    @Autowired
    ArticleTagService articleTagService;

    @ApiOperation("增加标签")
    @PostMapping("/createTag")
    public Result createTag(@RequestParam String tagName){
        return articleTagService.createTag(tagName);
    }

    @ApiOperation("查询标签")
    @GetMapping("/getArticleTag")
    public Result getArticleTag(@ApiParam("标签id，不传查看全部") @RequestParam(required = false) Long id){
        return articleTagService.getArticleTag(id);
    }
}
