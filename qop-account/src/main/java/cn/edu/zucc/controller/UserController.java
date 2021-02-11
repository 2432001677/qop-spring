package cn.edu.zucc.controller;

import cn.edu.zucc.pojo.QopUser;
import cn.edu.zucc.service.impl.QopUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("账户管理")
@RefreshScope
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private QopUserServiceImpl qopUserService;

    @ApiOperation("测试MySql")
    @GetMapping
    public List<QopUser> getAllUsers() {
        return qopUserService.queryAll();
    }
}
