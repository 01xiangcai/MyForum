package com.yao.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: registerDto
 * @Description: TODO
 * @author: long
 * @date: 2023/3/14 15:10
 */
@Data
public class RegisterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String username;

    private String email;

    @NotBlank
    private String password;


}
