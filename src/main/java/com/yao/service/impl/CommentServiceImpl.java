package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yao.common.CommentTypeEnum;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Comment;
import com.yao.entity.Question;
import com.yao.entity.User;
import com.yao.entity.dto.CommentDto;
import com.yao.entity.vo.CommentVo;
import com.yao.mapper.CommentMapper;
import com.yao.mapper.QuestionMapper;
import com.yao.mapper.UserMapper;
import com.yao.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    //创建评论
    @Override
    public Result createComment(CommentDto commentDto) {

        //评论类型不存在
        if (!CommentTypeEnum.exist(commentDto.getType())){
            return Result.fail(CustomizeResponseCode.COMMENT_INSERT_FAIL.getMessage());
        }

        Comment comment = new Comment();
        LocalDateTime now = LocalDateTime.now();

        BeanUtils.copyProperties(commentDto,comment);
        comment.setGmtCreate(now);

        int rows = commentMapper.insert(comment);

        if (rows==0){
            return Result.fail(CustomizeResponseCode.COMMENT_INSERT_FAIL.getMessage());
        }
        Question question = new Question();
        question.setId(comment.getParentId());
        question.setCommentCount(1);
        questionMapper.increaseComment(question);
        return Result.succ(CustomizeResponseCode.COMMENT_INSERT_SUCCESS.getMessage());
    }

    //查询评论
    @Override
    public Result commentList(Long parentId, Integer type) {
        //获取评论
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("parent_id",parentId).in("type",type);
        queryWrapper.orderByDesc("gmt_create");
        List<Comment> comments = commentMapper.selectList(queryWrapper);

        if (comments.size()==0||comments==null){
            return Result.succ(new ArrayList<>());
        }

        //获取评论人,去重   comment -> comment.getCommentator()
        Set<Long> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id",commentators);
        List<User> users = userMapper.selectList(userQueryWrapper);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        List<CommentVo> commentVos = comments.stream().map(comment -> {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            commentVo.setUsername(userMap.get(comment.getCommentator()).getUsername());
            commentVo.setAvatar(userMap.get(comment.getCommentator()).getAvatar());
            return commentVo;
        }).collect(Collectors.toList());

        return Result.succ(CustomizeResponseCode.COMMENT_FOUND_SUCCESS.getMessage(),commentVos);

    }
}
