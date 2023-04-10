package com.yao.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.deploy.util.StringUtils;
import com.yao.entity.Article;
import com.yao.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
    private static final String ARTICLE_RELEVENT = "article:relevent:";


    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ArticleService articleService;

    //热门文章增加阅读数
    public void incrementArticleReadCount(Long articleId) {
        String key = ARTICLE_READ_COUNT_PREFIX;
        //获取有序集合中的元素数量
        Long size = redisTemplate.opsForZSet().size(key);
        //如果超过指定数量则删除多余的
        if (size > 12) {
            redisTemplate.opsForZSet().removeRange(key, 0, size - 13);
        }
        redisTemplate.opsForZSet().incrementScore(key, String.valueOf(articleId), 1);

    }

    //相关文章,传文章id
    public void createReleventArticle(Long articleId) {
        //根据文章id去获取标签id
        Article article = articleService.getById(articleId);
        Long tagId = article.getTag();
        String key = ARTICLE_RELEVENT + tagId;
        Long size = redisTemplate.opsForZSet().size(key);
        if (size > 12) {
            redisTemplate.opsForZSet().removeRange(key, 0, size - 13);
        }
        redisTemplate.opsForZSet().incrementScore(key, String.valueOf(articleId), 1);

    }

    //获取热门文章
    public List<Article> getHotArticles(Integer count) {
        Set<String> articleIds = redisTemplate.opsForZSet().reverseRange(ARTICLE_READ_COUNT_PREFIX, 0, count - 1);

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        //按照 articleIds 中id在列表中出现的顺序，对查询结果进行排序。
        queryWrapper.in("id", articleIds).orderByAsc("field(id, " + StringUtils.join(articleIds, ",") + ")");
        List<Article> hotArticles = articleService.list(queryWrapper);

        return hotArticles;
    }

    //获取相关文章，根据标签id获取，可指定数量
    public List<Article> getReleventArticle(Integer tagId, Integer count) {
        Set<String> articleIds = redisTemplate.opsForZSet().reverseRange(ARTICLE_RELEVENT + tagId, 0, count - 1);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", articleIds).orderByAsc("field(id, " + StringUtils.join(articleIds, ",") + ")");
        List<Article> articleList = articleService.list(queryWrapper);
        return articleList;
    }

//    @Scheduled(cron = "0 0 0 * * ?")
//    public void clearExpiredData() {
//        redisTemplate.delete(ARTICLE_READ_COUNT_PREFIX);
//    }


}
