package com.yao.controller;


import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.dto.NotifitionDto;
import com.yao.service.NotifitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author long
 * @since 2023-03-31
 */
@Api(tags = "通知管理")
@RestController
@RequestMapping("/notifition")
public class NotifitionController {

    @Autowired
    NotifitionService notifitionService;

    @ApiOperation("消息列表")
    @GetMapping("/notifitions")
    public Result unreadCount(@RequestParam(defaultValue = "1") Integer currentPage,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @ApiParam(value = "用户id") @RequestParam Long id,
                              @ApiParam(value = "查看消息类型，0为未读，1为已读,不传参为查看全部") @RequestParam(required = false)  Integer readType){
        return notifitionService.notifitions(currentPage,pageSize,id,readType);
    }

    @ApiOperation("标记消息已读")
    @GetMapping("/markRead")
    public Result markRead(@ApiParam("通知的id") @RequestParam Long id){
        return notifitionService.markRead(id);
    }

    @ApiOperation("新建通知")
    @PostMapping("/createNotifition")
    public Result createNotifition(@RequestBody @Validated NotifitionDto notifitionDto, BindingResult result){
        if (result.hasErrors()){
            return Result.fail(CustomizeResponseCode.NOTIFITION_ERROR.getMessage());
        }
        return notifitionService.createNotifition(notifitionDto);
    }





}
