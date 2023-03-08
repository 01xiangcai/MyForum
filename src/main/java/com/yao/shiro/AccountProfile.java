package com.yao.shiro;

import lombok.Data;

import javax.annotation.security.DenyAll;
import java.io.Serializable;

/**
 * @className: AccountProfile
 * @Description: AccountProfile，这是为了登录成功之后返回的一个用户信息的载体，
 * @author: long
 * @date: 2023/3/7 0:08
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private String avatar;
}
