package com.yao.service;

import com.yao.common.Result;
import com.yao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.entity.dto.RegisterDto;
import com.yao.entity.dto.updateUserDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author long
 * @since 2023-03-06
 */
public interface UserService extends IService<User> {


    Result create(RegisterDto registerDto);

    Result deletedById(Long id);

    Result getUsers();


    Result updateUser(updateUserDto updateUserDto);
}
