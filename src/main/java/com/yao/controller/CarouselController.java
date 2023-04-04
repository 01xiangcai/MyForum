package com.yao.controller;


import com.yao.common.Result;
import com.yao.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author long
 * @since 2023-04-04
 */
@Api(tags = "轮播图管理")
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    CarouselService carouselService;

    @ApiOperation("上传页面轮播图")
    @PostMapping("/uploadCarousel")
    public Result uploadCarousel(@RequestPart("file") MultipartFile file,Integer type) {
        return carouselService.uploadCarousel(file,type);
    }

    @ApiOperation("查看轮播图地址列表")
    @GetMapping("/getCarousel")
    public Result getCarousel(@RequestParam Integer type){
        return carouselService.getCarousel(type);
    }



}
