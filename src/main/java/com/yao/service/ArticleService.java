package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.entity.dto.ArticleDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
public interface ArticleService extends IService<Article> {

    Result articleList(Integer currentPage);

    Result saveOrUpdateArticle(ArticleDto articleDto);

    Result articleByUserId(Long id, Integer currentPage, Integer size);

    Result increaseView(Long id);
}
