package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Article;
import com.yao.entity.ArticleComment;
import com.yao.entity.Notifition;
import com.yao.entity.Question;
import com.yao.entity.dto.NotifitionDto;
import com.yao.entity.vo.NotificationVo;
import com.yao.entity.vo.NotifitionVoIPage;
import com.yao.mapper.*;
import com.yao.service.NotifitionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-31
 */
@Service
public class NotifitionServiceImpl extends ServiceImpl<NotifitionMapper, Notifition> implements NotifitionService {

    @Autowired
    NotifitionMapper notifitionMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ArticleCommentMapper articleCommentMapper;

    @Autowired
    UserMapper userMapper;


    //创建通知
    @Override
    public Result createNotifition(NotifitionDto notifitionDto) {

        //新建消息notifition
        Notifition notifition = new Notifition();
        //将传输对象赋值给notifition
        BeanUtils.copyProperties(notifitionDto, notifition);
        //设置创建时间
        LocalDateTime now = LocalDateTime.now();
        notifition.setCreated(now);
        //存储
        int rows = notifitionMapper.insert(notifition);

        if (rows == 0) {
            return Result.fail(CustomizeResponseCode.NOTIFITION_INSERT_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.NOTIFITION_INSERT_SUCCESS.getMessage());
    }

    //消息列表，数据从表拿出来，没有经过处理
    @Override
    public Result notifitions(Integer currentPage, Integer pageSize, Long id, Integer readType) {

        //封装查询条件
        QueryWrapper<Notifition> queryWrapper = new QueryWrapper<>();

        //判断类型，0为未读，1为已读，不传参为查看全部
        if (readType == null) {
            queryWrapper.in("receiver", id).orderByDesc("created");
        } else {
            queryWrapper.in("readed", readType).in("receiver", id).orderByDesc("created");
        }

        //分页参数
        Page<Notifition> notificationPage = new Page<>(currentPage, pageSize);


        IPage<Notifition> notifitionIPage = notifitionMapper.selectPage(notificationPage, queryWrapper);

        if (notifitionIPage.getSize() == 0 || notifitionIPage == null) {
            return Result.fail(CustomizeResponseCode.NOTIFITION_FOUND_FAIL.getMessage());
        }

        return Result.succ(CustomizeResponseCode.NOTIFITION_FOUND_SUCCESS.getMessage(), notifitionIPage);
    }

    //返回前端页面的数据，数据经过处理
    @Override
    public Result getNotifitionViews(Integer currentPage, Integer pageSize, Long id, Integer readType) {
        //封装查询条件
        QueryWrapper<Notifition> queryWrapper = new QueryWrapper<>();

        //判断类型，0为未读，1为已读，不传参为查看全部
        if (readType == null) {
            queryWrapper.in("receiver", id).orderByDesc("created");
        } else {
            queryWrapper.in("readed", readType).in("receiver", id).orderByDesc("created");
        }
        //分页参数
        Page<Notifition> notificationPage = new Page<>(currentPage, pageSize);

        IPage<Notifition> notifitionIPage = notifitionMapper.selectPage(notificationPage, queryWrapper);
        List<Notifition> notifitions = notifitionIPage.getRecords();
        //存储处理后的NotifitionVo数据集
        ArrayList<NotificationVo> notificationVos = new ArrayList<>();

        for (Notifition notifition : notifitions) {
            //将数据封装成NotifitionDto，再转换成NotifitionVo返回
            NotifitionDto notifitionDto = new NotifitionDto();
            BeanUtils.copyProperties(notifition,notifitionDto);
            //调用方法将id转为具体的数据
            NotificationVo notificationVo = getNotificationVo(notifitionDto);
            //放入结果集
            notificationVos.add(notificationVo);
        }

        //最终返回数据
        NotifitionVoIPage notifitionVoIPage = new NotifitionVoIPage();
        notifitionVoIPage.setNotificationVoRecords(notificationVos);
        notifitionVoIPage.setCurrentPage(notifitionIPage.getCurrent());
        notifitionVoIPage.setPageSize(notifitionIPage.getSize());
        notifitionVoIPage.setTotal(notifitionIPage.getTotal());

        return Result.succ(CustomizeResponseCode.NOTIFITION_FOUND_SUCCESS.getMessage(),notifitionVoIPage);

    }

    //标记消息已读
    @Override
    public Result markRead(Long id) {
        //查找该通知
        Notifition notifition = notifitionMapper.selectById(id);
        //判断通知是否存在
        if (notifition == null || notifition.getReaded() == 1) {
            return Result.fail(CustomizeResponseCode.NOTIFITION_READORNOTGOUND.getMessage());
        }

        //存在就将消息状态设为已读
        notifition.setReaded(1);
        int rows = notifitionMapper.updateById(notifition);
        if (rows == 0) {
            return Result.fail(CustomizeResponseCode.NOTIFITION_READ_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.NOTIFITION_READ_SUCCESS.getMessage());
    }


    @Override
    public NotificationVo getNotificationVo(NotifitionDto notifitionDto) {
        //抽取变量
        Long notifier = notifitionDto.getNotifier();
        Long receiver = notifitionDto.getReceiver();
        Integer type = notifitionDto.getType();
        Long outerid = notifitionDto.getOuterid();

        //创建返回对象
        NotificationVo notificationVo = new NotificationVo();

        //找出对用的用户名
        notificationVo.setNotifierName(userMapper.selectById(notifier).getUsername());
        notificationVo.setReceiverName(userMapper.selectById(receiver).getUsername());
        //创建通知时间
        notificationVo.setCreated(LocalDateTime.now());

        //回复文章
        if (type == 1) {
            //找出文章信息赋值返回
            Article article = articleMapper.selectById(outerid);
            notificationVo.setOuterName(article.getTitle());
            notificationVo.setTypeName("文章");
        } else if (type == 2) {
            //回复问题
            Question question = questionMapper.selectById(outerid);
            notificationVo.setOuterName(question.getTitle());
            notificationVo.setTypeName("问题");
        } else if (type == 11) {
            //回复文章评论
            ArticleComment articleComment = articleCommentMapper.selectById(outerid);
            notificationVo.setOuterName(articleComment.getContent());
            notificationVo.setTypeName("文章评论");
        }
        return notificationVo;
    }

}
