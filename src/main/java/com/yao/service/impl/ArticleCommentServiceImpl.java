package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yao.common.CommentTypeEnum;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Article;
import com.yao.entity.ArticleComment;
import com.yao.entity.Question;
import com.yao.entity.User;
import com.yao.entity.dto.ArticleCommentDto;
import com.yao.entity.dto.NotifitionDto;
import com.yao.entity.vo.ArticleCommentVo;
import com.yao.entity.vo.CommentVo;
import com.yao.handler.NotifitionHandler;
import com.yao.mapper.*;
import com.yao.service.ArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.service.NotifitionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-26
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {

    @Autowired
    ArticleCommentMapper articleCommentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    NotifitionService notifitionService;

    @Autowired
    NotifitionHandler notifitionHandler;

    //增加评论
    @Override
    public Result createComment(ArticleCommentDto articleCommentDto) {
        //评论类型不存在
        if (!CommentTypeEnum.exist(articleCommentDto.getType())) {
            return Result.fail(CustomizeResponseCode.COMMENT_INSERT_FAIL.getMessage());
        }

        //评论赋值
        ArticleComment articleComment = new ArticleComment();
        LocalDateTime now = LocalDateTime.now();
        BeanUtils.copyProperties(articleCommentDto, articleComment);
        articleComment.setGmtCreate(now);

        //插入数据库
        int rows = articleCommentMapper.insert(articleComment);

        if (rows == 0) {
            return Result.fail(CustomizeResponseCode.COMMENT_INSERT_FAIL.getMessage());
        }

        //评论成功后创建消息通知
        NotifitionDto notifitionDto = new NotifitionDto();
        //评论人-->消息创建者
        notifitionDto.setNotifier(articleCommentDto.getCommentator());
        //文章或评论所属人-->通知对象
        if (articleCommentDto.getType()==1){
            //回复文章,找出文章所属人
            Article article = articleMapper.selectById(articleCommentDto.getParentId());
            notifitionDto.setReceiver(article.getCreator());
            //将通知属性设为1，表示回复文章
            notifitionDto.setType(1);
            //设置被回复的文章id
            notifitionDto.setOuterid(article.getId());
        }
        if (articleCommentDto.getType()==2){
            //回复文章评论，找出评论人
            ArticleComment articleComment1 = articleCommentMapper.selectById(articleCommentDto.getParentId());
            notifitionDto.setReceiver(articleComment1.getCommentator());
            //将通知属性设为11，表示回复文章评论
            notifitionDto.setType(11);
            //设置被回复评论id
            notifitionDto.setOuterid(articleComment1.getId());
        }
        //调用方法对通知消息进行存储
        notifitionService.createNotifition(notifitionDto);
        //同时向前端试试发送通知，让用户感知
        notifitionHandler.sendNotification(notifitionDto);


        //增加文章的评论数
        Article article = new Article();
        article.setId(articleComment.getParentId());
        article.setCommentCount(1);

        //增加评论的评论数，二级评论
        if (articleCommentDto.getType() == 2) {
            //找出该评论增加评论数
            Long parentId = articleCommentDto.getParentId();
            ArticleComment comment = articleCommentMapper.selectById(parentId);
            Integer commentCount = comment.getCommentCount();
            comment.setCommentCount(commentCount + 1);
            articleCommentMapper.updateById(comment);
            //同时增加文章的评论数,通过父评论id找到文章
            article.setId(comment.getParentId());
            article.setCommentCount(1);
        }

        //文章评论数插入数据库
        articleMapper.increaseComment(article);


        return Result.succ(CustomizeResponseCode.COMMENT_INSERT_SUCCESS.getMessage());


    }

    //查询评论
    @Override
    public Result commentList(Long parentId, Integer type) {

        //获取评论
        QueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("parent_id", parentId).in("type", type);
        queryWrapper.orderByDesc("gmt_create");
        List<ArticleComment> articleComments = articleCommentMapper.selectList(queryWrapper);

        if (articleComments.size() == 0 || articleComments == null) {
            return Result.succ(new ArrayList<>());
        }

        //获取评论人,去重   comment -> comment.getCommentator()
        Set<Long> commentators = articleComments.stream().map(ArticleComment::getCommentator).collect(Collectors.toSet());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", commentators);
        List<User> users = userMapper.selectList(userQueryWrapper);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));


        List<ArticleCommentVo> articleCommentVos = articleComments.stream().map(articleComment -> {
            ArticleCommentVo articleCommentVo = new ArticleCommentVo();
            BeanUtils.copyProperties(articleComment, articleCommentVo);
            articleCommentVo.setUsername(userMap.get(articleComment.getCommentator()).getUsername());
            articleCommentVo.setAvatar(userMap.get(articleComment.getCommentator()).getAvatar());
            return articleCommentVo;
        }).collect(Collectors.toList());

        return Result.succ(CustomizeResponseCode.COMMENT_FOUND_SUCCESS.getMessage(), articleCommentVos);

    }

}
