package com.yao.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: updateUserDto
 * @Description: TODO
 * @author: long
 * @date: 2023/4/5 21:30
 */
@Data
public class updateUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String email;


    private String password;

    //输入的旧密码
    private String oldPassword;
    //要改的新密码
    private String newPassword;
    //确认密码
    private String confirmPassword;
}
