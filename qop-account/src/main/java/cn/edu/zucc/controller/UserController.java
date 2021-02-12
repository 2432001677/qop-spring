package cn.edu.zucc.controller;

import cn.edu.zucc.exception.UnexpectedException;
import cn.edu.zucc.pojo.QopUser;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.utils.Response;
import cn.edu.zucc.vo.common.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return Response.getSuccessResponse(qopUserService.queryAll());
    }

    @ApiOperation("测试MySql")
    @GetMapping("/err")
    public ResultVo<List<QopUser>> getError() {
        throw new UnexpectedException("lalala");
    }
}
