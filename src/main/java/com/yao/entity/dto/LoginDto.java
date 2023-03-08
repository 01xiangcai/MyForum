package com.yao.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @className: LoginDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/7 1:07
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "名字不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
