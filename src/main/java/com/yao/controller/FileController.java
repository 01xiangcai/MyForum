package com.yao.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.yao.common.Result;
import com.yao.common.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/upload")
public class FileController {
    @Autowired
    FileService fileService;

    @ApiOperation("图片上传")
    @PostMapping("/images")
    public Result uploadImages( @RequestPart("file") MultipartFile file){
        return fileService.upload(file);
    }

    @ApiOperation("七牛云图片上传")
    @PostMapping("/qiniuImages")
    public Result qiNiuUploadImages( @RequestPart("file") MultipartFile file){
        return fileService.QiNiuUpload(file);
    }


}
