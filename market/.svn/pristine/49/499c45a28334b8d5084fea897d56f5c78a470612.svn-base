package com.winhands.modules.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winhands.common.utils.R;
import com.winhands.common.validator.Assert;
import com.winhands.modules.api.annotation.AuthIgnore;
import com.winhands.modules.api.service.UserService;

/**
 * 注册
 * @项目 oauth 
 * @author yuanl 
 * 2017年8月2日 
 * @Description
 */
@RestController
@RequestMapping("/api")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @AuthIgnore
    @PostMapping("register")
    public R register(String mobile, String password){
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        userService.save(mobile, password);

        return R.ok();
    }
}
