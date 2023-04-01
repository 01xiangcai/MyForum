package com.yao.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Question;
import com.yao.entity.User;
import com.yao.entity.dto.PublishQuestionDto;
import com.yao.entity.vo.QuestionRecords;
import com.yao.entity.vo.QuestionVo;
import com.yao.mapper.QuestionMapper;
import com.yao.mapper.UserMapper;
import com.yao.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.tools.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;


    //问题列表
    @Override
    public Result questions(Integer currentPage) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        Page<Question> questionPage = new Page<>(currentPage, 5);
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();


        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("gmt_create");


        IPage<Question> questionIPage = questionMapper.selectPage(questionPage, queryWrapper);
        long total = questionIPage.getTotal();
        long current = questionIPage.getCurrent();
        long size = questionIPage.getSize();
        List<Question> questions = questionIPage.getRecords();


        ArrayList<QuestionRecords> questionRecords = new ArrayList<>();

        for (Question question : questions) {
            //查找发布问题的用户
            User user = userMapper.selectById(question.getCreator());
            QuestionRecords questionRecords1 = new QuestionRecords();
            //问题复制给传输对象
            BeanUtils.copyProperties(question, questionRecords1);

            //添加用户信息
            questionRecords1.setCreator_name(user.getUsername());
            questionRecords1.setCreator_avatar(user.getAvatar());
            questionRecords1.setCreator_status(user.getStatus());
            questionRecords1.setCreator_lastLogin(user.getLastLogin());
            questionRecords1.setCreator_email(user.getEmail());

            questionRecords.add(questionRecords1);
        }

        QuestionVo questionVo = new QuestionVo();
        questionVo.setQuestionRecords(questionRecords);
        questionVo.setCurrentPage(current);
        questionVo.setPageSize(size);
        questionVo.setTotal(total);


        return Result.succ(CustomizeResponseCode.QUESTION_FOUND_SUCCESS.getMessage(), questionVo);
    }


    //根据用户id找问题
    @Override
    public Result selectById(Long id, Integer currentPage, Integer size) {

        //封装分页参数和查询参数
        Page<Question> questionPage = new Page<>(currentPage, size);
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("creator", id).in("deleted", 0).orderByDesc("gmt_create");

        IPage<Question> questionIPage = questionMapper.selectPage(questionPage, queryWrapper);

        return Result.succ(CustomizeResponseCode.QUESTION_FOUND_SUCCESS.getMessage(), questionIPage);
    }

    //新增问题
    @Override
    public Result saveorUpdateQuestion(PublishQuestionDto publishQuestionDto) {

        Long id = publishQuestionDto.getId();

        Question question = null;
        //记录操作是修改还是新增，0为新增。1为修改
        int a = 0;
        LocalDateTime now = LocalDateTime.now();

        //存在修改
        if (id != null) {
            question = questionMapper.selectById(id);
            Assert.isTrue(question.getCreator() == ShiroUtil.getProfile().getId(), "你没有权限编辑");
            if (question == null) {
                return Result.fail(CustomizeResponseCode.QUESTION_NOT_FOUND.getMessage());
            }
            question.setGmtModified(now);
            a = 1;
        } else {
            //不存在直接插入
            question = new Question();
            question.setGmtCreate(now);
            question.setCreator(ShiroUtil.getProfile().getId());
        }

        BeanUtils.copyProperties(publishQuestionDto, question, "id", "creator");


        QuestionServiceImpl.super.saveOrUpdate(question);
        if (a == 0) {
            return Result.succ(CustomizeResponseCode.QUESTION_INSERT_SUCCESS.getMessage());
        }
        return Result.succ(CustomizeResponseCode.QUESTION_UPDATE_SUCCESS.getMessage());
    }


    //删除问题
    @Override
    public Result deleteQuestion(Long id) {
        Question question = questionMapper.selectById(id);
        if (question == null) {
            return Result.fail(CustomizeResponseCode.QUESTION_NOT_FOUND.getMessage());
        }
        question.setDeleted(1);
        int rows = questionMapper.updateById(question);
        if (rows == 0) {
            return Result.fail(CustomizeResponseCode.QUESTION_DELETE_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.QUESTION_DELETE_SUCCES.getMessage());
    }


    //增加阅读数
    @Override
    public Result increaseView(Long id) {
        Question question = questionMapper.selectById(id);
        Integer viewCount = question.getViewCount();
        question.setViewCount(viewCount + 1);
        questionMapper.updateById(question);

        return Result.succ(CustomizeResponseCode.INCREASEVIEW_SUCCESS.getMessage());
    }
}
