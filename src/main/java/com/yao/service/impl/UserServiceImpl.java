package com.yao.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yao.common.Result;
import com.yao.common.constants.UserConstants;
import com.yao.entity.User;
import com.yao.entity.dto.RegisterDto;
import com.yao.mapper.UserMapper;
import com.yao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author long
 * @since 2023-03-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result createUser(RegisterDto registerDto) {
        //抽取变量
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String email = registerDto.getEmail();

        User user = new User();

        String md5Password = SecureUtil.md5(password+ UserConstants.USER_SLAT);
        LocalDateTime time = LocalDateTime.now();

        user.setUsername(username);
        user.setPassword(md5Password);
        user.setEmail(email);
        user.setCreated(time);
        user.setStatus(1);

        int insert = userMapper.insert(user);

        if (insert!=1){
            return Result.fail("新增失败");
        }

        return Result.succ("新增成功");

    }


    @Override
    public Result deletedById(Long id) {
        int rows = userMapper.deleteById(id);
        if (rows==0){
            return Result.fail("删除失败");
        }
        return Result.succ("删除成功");
    }

    @Override
    public Result getUsers() {
        List<User> users = userMapper.selectList(null);
        return Result.succ("查询所有用户成功",users);
    }
}
