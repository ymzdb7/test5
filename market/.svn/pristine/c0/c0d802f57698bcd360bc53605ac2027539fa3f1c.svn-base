package com.winhands.modules.api.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winhands.common.utils.R;
import com.winhands.common.validator.Assert;
import com.winhands.modules.api.annotation.AuthIgnore;
import com.winhands.modules.api.service.TokenService;
import com.winhands.modules.api.service.UserService;

/**
 * API登录授权
 * @项目 oauth 
 * @author yuanl 
 * 2017年8月2日 
 * @Description
 */
@RestController
@RequestMapping("/api")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

	/**
     * 登录
     */
    @AuthIgnore
    @PostMapping("login")
    public R login(String mobile, String password){
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        //用户登录
        long userId = userService.login(mobile, password);

        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return R.ok(map);
    }

}
