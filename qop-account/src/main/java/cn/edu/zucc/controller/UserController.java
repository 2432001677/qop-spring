package cn.edu.zucc.controller;

import cn.edu.zucc.pojo.QopUser;
import cn.edu.zucc.service.impl.QopUserServiceImpl;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private QopUserServiceImpl qopUserService;

    @GetMapping
    public List<QopUser> getAllUsers() {
        return qopUserService.queryAll();
    }
}
