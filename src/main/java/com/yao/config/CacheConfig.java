package com.yao.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: CacheConfig
 * @Description: 缓存配置类
 * @author: long
 * @date: 2023/4/3 16:22
 */
@Configuration
@EnableCaching
public class CacheConfig {

    //通知对象id缓存
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache("notificationReceiverIdCache"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
