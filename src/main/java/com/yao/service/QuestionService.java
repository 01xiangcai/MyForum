package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.entity.dto.PublishQuestionDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
public interface QuestionService extends IService<Question> {

    Result questions(Integer currentPage);

    Result saveorUpdateQuestion(PublishQuestionDto publishQuestionDto);

    Result deleteQuestion(Long id);

    Result selectById(Long id);
}
