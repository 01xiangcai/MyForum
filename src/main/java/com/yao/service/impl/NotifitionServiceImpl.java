package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Notifition;
import com.yao.entity.dto.NotifitionDto;
import com.yao.mapper.NotifitionMapper;
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

    //消息列表
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

        List<Notifition> notifitions = notifitionMapper.selectList(null);


        IPage<Notifition> notifitionIPage = notifitionMapper.selectPage(notificationPage, queryWrapper);

        if (notifitionIPage.getSize() == 0 || notifitionIPage == null) {
            return Result.fail(CustomizeResponseCode.NOTIFITION_FOUND_FAIL.getMessage());
        }

        return Result.succ(CustomizeResponseCode.NOTIFITION_FOUND_SUCCESS.getMessage(), notifitionIPage);
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
}
