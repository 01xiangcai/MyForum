package com.yao.Demo;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @className: testController
 * @Description: TODO
 * @author: long
 * @date: 2023/3/19 21:55
 */
@Api(tags = "测试")
@RestController
public class testController {
    @GetMapping("/timeTest")
    public OrderInfo timeTest() {
        OrderInfo order = new OrderInfo();
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(new Date());
        return order;
    }


}
