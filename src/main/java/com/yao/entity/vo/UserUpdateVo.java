package com.yao.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: userUpdateVo
 * @Description: 修改用户后返回给前端的参数，方便存在store共享
 * @author: long
 * @date: 2023/4/7 2:26
 */
@Data
public class UserUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String avatar;

    private String email;

    private String username;

}
