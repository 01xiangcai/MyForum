package com.yao.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: NotifitionVoIPage
 * @Description: 返回前端页面的分页数据
 * @author: long
 * @date: 2023/4/2 2:34
 */
@Data
public class NotifitionVoIPage implements Serializable {
    private static final long serialVersionUID = 1L;

    List<NotificationVo> notificationVoRecords;

    //数据总数
    private Long total;

    private Long  pageSize;

    private Long currentPage;

}
