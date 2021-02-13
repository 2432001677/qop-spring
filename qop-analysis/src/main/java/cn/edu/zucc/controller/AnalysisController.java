package cn.edu.zucc.controller;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "分析")
@RefreshScope
@RestController
@RequestMapping("analysis")
public class AnalysisController {
    @Resource
    private QopUserServiceImpl qopUserService;

    @ApiOperation("测试MySql")
    @GetMapping
    public List<QopUser> getAllUsers() {
        return qopUserService.queryAll();
    }
}