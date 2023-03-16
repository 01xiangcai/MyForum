package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Question;
import com.yao.entity.User;
import com.yao.entity.dto.PublishQuestionDto;
import com.yao.entity.vo.QuestionVo;
import com.yao.mapper.QuestionMapper;
import com.yao.mapper.UserMapper;
import com.yao.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
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
        List<Question> questions = questionIPage.getRecords();
        ArrayList<QuestionVo> questionVos = new ArrayList<>();

        for (Question question : questions) {
            //查找发布问题的用户
            User user = userMapper.selectById(question.getCreator());
            QuestionVo questionVo = new QuestionVo();
            //问题复制给传输对象
            BeanUtils.copyProperties(question, questionVo);

            //添加用户信息
            questionVo.setCreator_name(user.getUsername());
            questionVo.setCreator_avatar(user.getAvatar());
            questionVo.setCreator_status(user.getStatus());
            questionVo.setCreator_lastLogin(user.getLastLogin());
            questionVo.setCreator_email(user.getEmail());

            questionVos.add(questionVo);
        }


        return Result.succ(CustomizeResponseCode.QUESTION_FOUND_SUCCESS.getMessage(), questionVos);
    }


    //根据用户id找问题
    @Override
    public Result selectById(Long id) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("creator", id).in("deleted",0);

        User user = userMapper.selectById(id);

        if (user==null){
            return Result.fail(CustomizeResponseCode.USER_NOT_FOUND.getMessage());
        }

        List<Question> questions = questionMapper.selectList(queryWrapper);


        if (questions.size() == 0 || questions == null) {
            return Result.fail(CustomizeResponseCode.QUESTION_NOT_FOUND.getMessage());
        }


        ArrayList<QuestionVo> questionVos = new ArrayList<>();

        for (Question question : questions) {

            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(question, questionVo);

            questionVo.setCreator_name(user.getUsername());
            questionVo.setCreator_avatar(user.getAvatar());
            questionVo.setCreator_status(user.getStatus());
            questionVo.setCreator_lastLogin(user.getLastLogin());
            questionVo.setCreator_email(user.getEmail());

            questionVos.add(questionVo);

        }



        return Result.succ(CustomizeResponseCode.QUESTION_FOUND_SUCCESS.getMessage(), questionVos);
    }

    //新增问题
    @Override
    public Result saveorUpdateQuestion(PublishQuestionDto publishQuestionDto) {
      /*  @NotBlank String title = publishQuestionDto.getTitle();
        @NotBlank String description = publishQuestionDto.getDescription();
        @NotBlank String tag = publishQuestionDto.getTag();
        @NotBlank Long creator = publishQuestionDto.getCreator();*/
        Long id = publishQuestionDto.getId();

        Question question = null;
        //记录操作是修改还是新增，0为新增。1为修改
        int a = 0;
        LocalDateTime now = LocalDateTime.now();

        //存在修改
        if (id != null) {
            question = questionMapper.selectById(id);
            if (question == null) {
                return Result.fail(CustomizeResponseCode.QUESTION_NOT_FOUND.getMessage());
            }
            question.setGmtModified(now);
            a = 1;
        } else {
            //不存在直接插入
            question = new Question();
            /*question.setTitle(title);
            question.setDescription(description);
            question.setCreator(creator);
            question.setTag(tag);*/
            question.setGmtCreate(now);
        }

        BeanUtils.copyProperties(publishQuestionDto, question, "id");


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
}
