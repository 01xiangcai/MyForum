package com.yao.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @className: JwtToken
 * @Description: 我们现在采用了jwt的方式，所以这里我们自定义一个JwtToken，
 * 来完成shiro的supports方法。
 * @author: long
 * @date: 2023/3/6 23:54
 */

public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }

}
