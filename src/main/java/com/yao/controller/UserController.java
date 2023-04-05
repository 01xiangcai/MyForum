package com.yao.controller;


import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.User;
import com.yao.entity.dto.RegisterDto;
import com.yao.entity.dto.updateUserDto;
import com.yao.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author long
 * @since 2023-03-06
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("根据用户id查询用户")
    @GetMapping("/{id}")
    public Result byId(@PathVariable("id") Long id) {
        User userById = userService.getById(id);
        if (userById == null) {
            return Result.fail("用户不存在");
        }
        return Result.succ("查询用户成功", userById);
    }

    @ApiOperation("查询所有用户")
    @GetMapping("/users")
    public Result users() {
        return userService.getUsers();
    }

    @ApiOperation("新增或修改用户")
    @PostMapping("/create")
    public Result create(@RequestBody @Validated RegisterDto registerDto, BindingResult result) {
        if (result.hasErrors()) {
            return Result.fail(CustomizeResponseCode.PARAMETER_ERROR.getMessage());
        }
        return userService.create(registerDto);
    }

    @ApiOperation("修改用户")
    @PostMapping("/update")
    public Result update(@RequestBody updateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }


    @ApiOperation("删除用户")
    @GetMapping("/deleted{id}")
    public Result deleted(@PathVariable("id") Long id) {
        return userService.deletedById(id);
    }


}
