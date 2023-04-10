package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-04-08
 */
public interface ArticleTagService extends IService<ArticleTag> {

    Result createTag(String tagName);

    Result getArticleTag(Long id);
}
