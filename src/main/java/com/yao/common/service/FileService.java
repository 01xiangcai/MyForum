package com.yao.common.service;


import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileService {
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.AccessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.file.AccessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;


    public Result upload(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            //上传文件流
            InputStream inputStream = file.getInputStream();
            fileName = UUID.randomUUID().toString() + fileName;
            String path = new DateTime().toString("yyyy-MM-dd");
            fileName = path + "/" + fileName;

            ossClient.putObject(bucketname,fileName,inputStream);
            //关闭OSSClient
            ossClient.shutdown();

            String url = "https://" + bucketname + "." + endpoint + '/' + fileName;

            return Result.succ(CustomizeResponseCode.UPLOAD_SUCCESS.getMessage(),url);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(CustomizeResponseCode.UPLOAD_FAIL.getMessage());
        }

    }


}
