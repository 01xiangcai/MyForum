package com.yao.common.service;

import com.yao.entity.Article;
import com.yao.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @className: HotService
 * @Description: redis存储热门文章id
 * @author: long
 * @date: 2023/3/30 15:44
 */
@Service
public class HotService {

    private static final String ARTICLE_READ_COUNT_PREFIX = "article:read:count:";


    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ArticleService articleService;

    public void incrementArticleReadCount(Long articleId) {
        String key = ARTICLE_READ_COUNT_PREFIX;
        redisTemplate.opsForZSet().incrementScore(key, String.valueOf(articleId), 1);
        //获取有序集合中的元素数量
        Long size = redisTemplate.opsForZSet().size(key);
        //如果超过指定数量则删除多余的
        if (size > 10) {
            redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
        }
    }

    public List<Article> getHotArticles(Integer count) {
        Set<String> articleIds = redisTemplate.opsForZSet().reverseRange(ARTICLE_READ_COUNT_PREFIX, 0, count - 1);
        List<Article> hotArticles = new ArrayList<>();
        for (String articleId : articleIds) {
//            Long id = Long.valueOf(articleId.substring(ARTICLE_READ_COUNT_PREFIX.length()));
            Article article = articleService.getById(articleId);
            if (article != null) {
                hotArticles.add(article);
            }
        }
        return hotArticles;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearExpiredData() {
        redisTemplate.delete(ARTICLE_READ_COUNT_PREFIX);
    }


}
