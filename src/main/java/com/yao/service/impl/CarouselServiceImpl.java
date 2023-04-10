package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.common.myEnum.UploadImageType;
import com.yao.common.service.FileService;
import com.yao.entity.Carousel;
import com.yao.mapper.CarouselMapper;
import com.yao.service.CarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  轮播图服务实现类
 * </p>
 *
 * @author long
 * @since 2023-04-04
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Autowired
    FileService fileService;

    @Autowired
    CarouselMapper carouselMapper;

    //上传轮播图
    @Override
    public Result uploadCarousel(MultipartFile file, Integer type) {
        //调用服务上传图片得到返回的地址
        String url = fileService.QiNiuUpload(file, UploadImageType.CAROUSEL.getImageType());
        //存到数据库的轮播图表
        Carousel carousel = new Carousel();
        carousel.setUrl(url);
        carousel.setType(type);
        carousel.setCreateTime(LocalDateTime.now());
        int rows = carouselMapper.insert(carousel);
        if (rows==0){
            return Result.fail(CustomizeResponseCode.UPLOAD_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.UPLOAD_SUCCESS.getMessage(),url);
    }

    //轮播图地址列表
    @Override
    public Result getCarousel(Integer type) {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("url").in("type",type).orderByDesc("create_time");
        List<Carousel> carousels = carouselMapper.selectList(queryWrapper);
        return Result.succ(CustomizeResponseCode.CAROUSEL_FOUND_SUCCESS.getMessage(),carousels);
    }
}
