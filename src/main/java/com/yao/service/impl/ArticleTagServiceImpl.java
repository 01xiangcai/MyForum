package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.ArticleTag;
import com.yao.mapper.ArticleTagMapper;
import com.yao.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author long
 * @since 2023-04-08
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Autowired
    ArticleTagMapper articleTagMapper;

    //增加标签
    @Override
    public Result createTag(String tagName) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setTagName(tagName);
        articleTag.setGmtCreate(LocalDateTime.now());
        int rows = articleTagMapper.insert(articleTag);
        if (rows==0){
            return Result.fail(CustomizeResponseCode.ARTICLE_CREATETAG_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.ARTICLE_CREATETAG_SUCCESS.getMessage());
    }


    @Override
    public Result getArticleTag(Long id) {
        //不传id查看全部
        if (id==null){
            List<ArticleTag> articleTags = articleTagMapper.selectList(null);
            return Result.succ(CustomizeResponseCode.ARTICLE_GETATETAG_SUCCESS.getMessage(),articleTags);
        }
        //根据id查看
        ArticleTag articleTag = articleTagMapper.selectById(id);
        return Result.succ(CustomizeResponseCode.ARTICLE_GETATETAG_SUCCESS.getMessage(),articleTag);
    }
}
