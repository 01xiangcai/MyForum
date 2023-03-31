package com.yao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.common.Result;
import com.yao.entity.Notifition;
import com.yao.entity.dto.NotifitionDto;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author long
 * @since 2023-03-31
 */
public interface NotifitionService extends IService<Notifition> {

    Result notifitions(Integer currentPage, Integer pageSize, Long id, Integer readType);

    Result createNotifition(NotifitionDto notifitionDto);

    Result markRead(Long id);
}
