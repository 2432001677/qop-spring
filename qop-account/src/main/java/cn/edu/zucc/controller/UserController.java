package cn.edu.zucc.controller;

import cn.edu.zucc.exception.UnexpectedException;
import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.common.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bruce
 * @since 02-11-2021
 */
@Api(tags = "账户管理")
@RefreshScope
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private QopUserServiceImpl qopUserService;

    @ApiOperation("测试MySql")
    @GetMapping
    public ResultVo<List<QopUser>> getAllUsers() {
        return ResponseBuilder.buildSuccessResponse(qopUserService.queryAll());
    }

    @ApiOperation("测试创建账户")
    @PostMapping
    public ResultVo<QopUser> createUser(@RequestBody QopUser qopUser) {
        return ResponseBuilder.buildSuccessResponse(qopUserService.addUser(qopUser));
    }

    @ApiOperation("测试error")
    @GetMapping("/err")
    public ResultVo<List<QopUser>> getError() {
        throw new UnexpectedException("lalala");
    }
}
