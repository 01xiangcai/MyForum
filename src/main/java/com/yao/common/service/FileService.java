package com.yao.common.service;


import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.common.myEnum.UploadImageType;
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

    @Value("${qiniuyun.kodo.file.accessKey}")
    private String QiNiuAccessKeyId;

    @Value("${qiniuyun.kodo.file.secretKey}")
    private String QiNiuAccessKeySecret;

    @Value("${qiniuyun.kodo.file.bucket}")
    private String QiNiuBucketname;

    @Value("${qiniuyun.kodo.file.domain}")
    private String QiNiuDomain;


    //阿里云
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            //上传文件流
            InputStream inputStream = file.getInputStream();
            fileName = UUID.randomUUID().toString() + fileName;
            String path = new DateTime().toString("yyyy-MM-dd");
            fileName = "头像"+ path + "/" + fileName;

            ossClient.putObject(bucketname, fileName, inputStream);
            //关闭OSSClient
            ossClient.shutdown();

            String url = "https://" + bucketname + "." + endpoint + '/' + fileName;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据图片类型进行上传存储，uploadImageType,七牛云
    public String QiNiuUpload(MultipartFile file, Integer type) {
        //存储空间的根文件名
        String rootFileName = null;
        if (type == 0) {
            rootFileName = UploadImageType.CAROUSEL.getImageTypeName();
        } else {
            rootFileName = UploadImageType.ORTHER.getImageTypeName();
        }

        String fileName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();

            // 构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration();
            UploadManager uploadManager = new UploadManager(cfg);

            //文件名
            fileName = UUID.randomUUID().toString() + fileName;
            String path = new DateTime().toString("yyyy-MM-dd");
            fileName = rootFileName + "/" + path + "/" + fileName;

            // 生成上传凭证，然后准备上传
            Auth auth = Auth.create(QiNiuAccessKeyId, QiNiuAccessKeySecret);
            String s = auth.uploadToken(QiNiuBucketname);
            uploadManager.put(inputStream, fileName, s, null, null);
            String url = "http://" + QiNiuDomain + "/" + fileName;
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
