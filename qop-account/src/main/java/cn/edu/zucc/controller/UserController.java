package cn.edu.zucc.controller;

import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Resource
    private QopUserServiceImpl qopUserService;

    @ApiOperation("login")
    @PostMapping("/login")
    public ResultVo<Void> login(@RequestBody LoginVo loginVo, HttpServletResponse response) {
        if (loginVo == null || StringUtils.isBlank(loginVo.getUserName()) || StringUtils.isBlank(loginVo.getPassword())) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        response.setHeader("Authorization", TokenUtils.sign(qopUserService.login(loginVo), tokenSecret, issuer));
        log.info("Login");
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultVo<Void> register(@RequestBody RegisterVo registerVo) {
        if (registerVo == null || StringUtils.isBlank(registerVo.getUserName()) || StringUtils.isBlank(registerVo.getNickName()) || StringUtils.isBlank(registerVo.getPassword())) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        qopUserService.register(registerVo);
        return ResponseBuilder.buildSuccessResponse();
    }
}
