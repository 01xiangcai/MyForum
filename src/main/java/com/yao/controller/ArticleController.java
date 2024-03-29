package com.yao.controller;


import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.common.service.HotService;
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
import java.util.List;

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
    @Autowired
    HotService hotService;

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
        //增加热门文章和相关文章的分数
        hotService.incrementArticleReadCount(id);
        hotService.createReleventArticle(id);
        return Result.succ(CustomizeResponseCode.ARTICLE_FOUND_SUCCESS.getMessage(),article);
    }

    @ApiOperation("查看热门文章")
    @GetMapping("/article/hot")
    public Result articleHot(Integer count){
        List<Article> hotArticles = hotService.getHotArticles(count);
        if (hotArticles.size()==0||hotArticles==null){
            return Result.fail(CustomizeResponseCode.ARTICLE_NOT_FOUND.getMessage());
        }
        return Result.succ(CustomizeResponseCode.ARTICLE_FOUND_SUCCESS.getMessage(),hotArticles);
    }

    @ApiOperation("查看相关文章")
    @GetMapping("/article/relevent")
    public Result articleRelevent(@RequestParam Integer tagId,@RequestParam Integer count){
        List<Article> releventArticles = hotService.getReleventArticle(tagId, count);
        if (releventArticles.size()==0||releventArticles==null){
            return Result.fail(CustomizeResponseCode.ARTICLE_NOT_FOUND.getMessage());
        }
        return Result.succ(CustomizeResponseCode.ARTICLE_FOUND_SUCCESS.getMessage(),releventArticles);
    }

    //根据用户id查看文章
    @ApiOperation("根据用户id查看文章")
    @GetMapping("/articleByUserId")
    public Result articleByUserId(@RequestParam Long userId,
                                  @RequestParam(defaultValue = "1") Integer currentPage,
                                  @RequestParam(defaultValue = "5") Integer size){
        return articleService.articleByUserId(userId,currentPage,size);
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


    @ApiOperation("增加阅读数")
    @GetMapping("/increaseView")
    public Result increaseView(@RequestParam Long id){
        if (id==null){
            return Result.fail(CustomizeResponseCode.ARTICLE_NOT_FOUND.getMessage());
        }
        return articleService.increaseView(id);
    }



}
