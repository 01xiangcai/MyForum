package com.yao.config;

import com.yao.shiro.AccountRealm;
import com.yao.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;

import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @className: ShiroConfig
 * @Description: shiro启用注解拦截控制器
 * @author: long
 * @date: 2023/3/6 23:41
 */
@Configuration
@EnableCaching
public class ShiroConfig {

    @Autowired
    JwtFilter jwtFilter;

    /*
    * @param redisSessionDAO:
     * @return SessionManager
    * @author long
    * @description sessionManager() 方法返回一个 SessionManager 实例，并将 RedisSessionDAO 作为参数传入。
    * 在实现中，使用 DefaultWebSessionManager 类作为会话管理器，并设置 sessionDAO 为传入的 RedisSessionDAO 实例。
    * @date 2023/3/9 22:00
    */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /*
    * @param accountRealm:
	 * @param sessionManager:
	 * @param redisCacheManager:
     * @return DefaultWebSecurityManager
    * @author long
    * @description securityManager() 方法返回一个 DefaultWebSecurityManager 实例，该实例需要 AccountRealm、SessionManager 和 RedisCacheManager 作为参数。
    * 在实现中，将参数传入实例，并将 Shiro 自带的 Session 关闭。
    * 最后，将 subjectDAO 设置为自定义的 DefaultSubjectDAO 实例。
    * @date 2023/3/9 22:01
    */
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        /*
         * 关闭shiro自带的session，详情见文档
         */
        /*DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);*/
        return securityManager;
    }

    /*
    * @param :
     * @return ShiroFilterChainDefinition
    * @author long
    * @description shiroFilterChainDefinition() 方法返回一个 ShiroFilterChainDefinition 实例，并添加了一个过滤器映射：/** 路径的请求需要使用 JwtFilter 进行处理。
    * @date 2023/3/9 22:02
    */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**", "jwt"); // 主要通过注解方式校验权限
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }

    /*
    * @param securityManager:
	 * @param shiroFilterChainDefinition:
     * @return ShiroFilterFactoryBean
    * @author long
    * @description shiroFilterFactoryBean() 方法返回一个 ShiroFilterFactoryBean 实例，其中传入了 SecurityManager 和 ShiroFilterChainDefinition 作为参数。在实现中，
    * 设置了过滤器映射和过滤器链，并将自定义的 JwtFilter 添加到过滤器中。
    * @date 2023/3/9 22:04
    */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    // 开启注解代理（默认好像已经开启，可以不要）
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        return creator;
    }


}
