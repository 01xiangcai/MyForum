package com.yao.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.common.constants.UserConstants;
import com.yao.entity.User;
import com.yao.entity.dto.RegisterDto;
import com.yao.entity.dto.updateUserDto;
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
 * 服务实现类
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
    public Result create(RegisterDto registerDto) {
        //抽取变量
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String email = registerDto.getEmail();

        LocalDateTime time = LocalDateTime.now();
        //密码加密
        String md5Password = SecureUtil.md5(password + UserConstants.USER_SLAT);


        User user = new User();
        user.setUsername(username);
        user.setPassword(md5Password);
        user.setEmail(email);
        user.setCreated(time);
        user.setStatus(1);

        int insert = userMapper.insert(user);
        if (insert == 0) {
            return Result.fail("新增失败");
        }
        return Result.succ("新增成功");
    }

    //修改用户,可能修改单个属性，可能修改多个属性，判断处理
    @Override
    public Result updateUser(updateUserDto updateUserDto) {
        User user = userMapper.selectById(updateUserDto.getUserId());

        String inputNewPassword = updateUserDto.getNewPassword();
        //修改密码
        if (inputNewPassword!=null&&!inputNewPassword.isEmpty()){
            //校验输入的旧密码是否与数据库的密码相等
            String inputOldPassword = SecureUtil.md5(updateUserDto.getOldPassword() + UserConstants.USER_SLAT);
            if (!inputOldPassword.equals(user.getPassword())){
                return Result.fail(CustomizeResponseCode.OLD_PASSWORD_ERROR.getMessage());
            }
            //检验通过，设置新密码
            String newPassword = SecureUtil.md5(inputNewPassword + UserConstants.USER_SLAT);
            user.setPassword(newPassword);
        }
        //修改用户名
        if (updateUserDto.getUsername()!=null){
            user.setUsername(updateUserDto.getUsername());
        }
        //修改邮箱
        if (updateUserDto.getEmail()!=null){
            user.setEmail(updateUserDto.getEmail());
        }

        //存到数据库
        int rows = userMapper.updateById(user);
        if (rows==0){
            return Result.fail(CustomizeResponseCode.USER_UPDATE_FAIL.getMessage());
        }
        return Result.succ(CustomizeResponseCode.USER_UPDATE_SUCCESS.getMessage());

    }

    @Override
    public Result deletedById(Long id) {
        int rows = userMapper.deleteById(id);
        if (rows == 0) {
            return Result.fail("删除失败");
        }
        return Result.succ("删除成功");
    }

    @Override
    public Result getUsers() {
        List<User> users = userMapper.selectList(null);
        return Result.succ("查询所有用户成功", users);
    }
}
