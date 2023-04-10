package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-04-04
 */
public interface CarouselService extends IService<Carousel> {

    Result uploadCarousel(MultipartFile file, Integer type);

    Result getCarousel(Integer type);

}
