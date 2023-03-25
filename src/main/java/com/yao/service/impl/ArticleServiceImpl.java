package com.yao.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Article;
import com.yao.entity.User;
import com.yao.entity.dto.ArticleDto;
import com.yao.entity.vo.ArticleRecords;
import com.yao.entity.vo.ArticleVo;
import com.yao.mapper.ArticleMapper;
import com.yao.mapper.UserMapper;
import com.yao.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.tools.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;


    //查看文章列表
    @Override
    public Result articleList(Integer currentPage) {
        if (currentPage < 1) {
            currentPage = 1;
        }

        //设置分页参数，构造查询条件
        Page<Article> page = new Page<>(currentPage, 5);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0).orderByDesc("gmt_create");

        //获取分页文章数据
        IPage<Article> articleIPage = articleMapper.selectPage(page, queryWrapper);

        if (articleIPage == null) {
            return Result.fail(CustomizeResponseCode.ARTICLE_NOT_FOUND.getMessage());
        }

        List<Article> articleList = articleIPage.getRecords();
        long total = articleIPage.getTotal();
        long current = articleIPage.getCurrent();
        long size = articleIPage.getSize();

        //定义一个集合装遍历出来的articleRecords
        ArrayList<ArticleRecords> articleRecordsArrayList = new ArrayList<>();


        //根据文章查询用户信息
        for (Article article : articleList) {
            User user = userMapper.selectById(article.getCreator());
            //将用户信息封装到数据传输对象articleRecords中
            ArticleRecords articleRecords = new ArticleRecords();

            //添加用户信息
            articleRecords.setCreator_avatar(user.getAvatar());
            articleRecords.setCreator_name(user.getUsername());
            articleRecords.setCreator_email(user.getEmail());
            articleRecords.setCreator_status(user.getStatus());
            articleRecords.setCreator_lastLogin(user.getLastLogin());

            //添加文章信息
            BeanUtils.copyProperties(article, articleRecords);

            //放到集合
            articleRecordsArrayList.add(articleRecords);
        }

        //返回前端对象
        ArticleVo articleVo = new ArticleVo();
        //数据放到对象
        articleVo.setArticleRecords(articleRecordsArrayList);
        articleVo.setTotal(total);
        articleVo.setCurrentPage(current);
        articleVo.setPageSize(size);

        return Result.succ(CustomizeResponseCode.ARTICLE_FOUND_SUCCESS.getMessage(), articleVo);

    }


    //新增或更新
    @Override
    public Result saveOrUpdateArticle(ArticleDto articleDto) {

        Article article = null;
        //当前时间
        LocalDateTime now = LocalDateTime.now();

        //记录更新还是新增，0新增，1更新
        int a = 0;

        //有id，更新操作
        if (articleDto.getId() != null) {
            article = articleMapper.selectById(articleDto.getId());
            if (article==null){
                return Result.fail(CustomizeResponseCode.ARTICLE_NOT_FOUND.getMessage());
            }
//            Assert.isTrue(article.getCreator()== ShiroUtil.getProfile().getId(),"你没有权限编辑");
            article.setGmtModified(now);
            a = 1;
        } else {
            //新增
            article = new Article();
            article.setGmtCreate(now);
        }

        BeanUtils.copyProperties(articleDto, article);

        ArticleServiceImpl.super.saveOrUpdate(article);

        if (a == 0) {
            return Result.succ(CustomizeResponseCode.ARTICLE_INSERT_SUCCESS.getMessage());
        }
        return Result.succ(CustomizeResponseCode.ARTICLE_UPDATE_SUCCESS.getMessage());

    }


    //根据用户查看文章列表
    @Override
    public Result articleByUserId(Long id, Integer currentPage) {
        //设置分页参数
        Page<Article> articlePage = new Page<>(currentPage, 5);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator",id).orderByDesc("gmt_create").eq("deleted",0);

        IPage<Article> articleIPage = articleMapper.selectPage(articlePage, queryWrapper);

        return Result.succ(CustomizeResponseCode.ARTICLE_FOUND_SUCCESS.getMessage(),articleIPage);
    }
}
