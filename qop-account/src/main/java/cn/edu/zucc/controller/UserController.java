package cn.edu.zucc.controller;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.exception.WrongPasswordException;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.utils.CryptUtils;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Bruce
 * @since 02-11-2021
 */
@Api(tags = "账户管理")
@Slf4j
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
        qopUser.setPassword(CryptUtils.cryptAccountPasswd(qopUser.getPassword()));
        return ResponseBuilder.buildSuccessResponse(qopUserService.addUser(qopUser));
    }

    /*
     * 失败返回login
     * */

    @ApiOperation("login")
    @PostMapping("/login")
    public ResultVo<Void> login(@RequestBody LoginVo loginVo, HttpServletResponse response) {
        if (loginVo == null || StringUtils.isBlank(loginVo.getUserName()) || StringUtils.isBlank(loginVo.getPassword())) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        response.setHeader("Authorization", TokenUtils.sign(qopUserService.login(loginVo)));
        log.info("Login");
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultVo<Void> register(@RequestBody RegisterVo registerVo) {
        qopUserService.register(registerVo);
        return ResponseBuilder.buildSuccessResponse();
    }
}
