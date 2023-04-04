package com.yao.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.yao.common.Result;
import com.yao.common.service.FileService;
import com.yao.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @className: FileController
 * @Description: TODO
 * @author: long
 * @date: 2023/3/30 21:36
 */
@Api(tags = "测试上传管理")
@RestController
@RequestMapping("/upload")
public class FileController {
    @Autowired
    FileService fileService;



    @ApiOperation("阿里云图片上传")
    @PostMapping("/images")
    public Result uploadImages(@RequestPart("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @ApiOperation("七牛云图片上传")
    @PostMapping("/qiniuImages")
    public Result qiNiuUploadImages(@RequestPart("file") MultipartFile file) {
        String url = fileService.QiNiuUpload(file, 0);
        return Result.succ(url);
    }



}
