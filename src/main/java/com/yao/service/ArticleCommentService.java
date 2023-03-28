package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.entity.dto.ArticleCommentDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-03-26
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    Result createComment(ArticleCommentDto articleCommentDto);

    Result commentList(Long parentId, Integer type);
}
