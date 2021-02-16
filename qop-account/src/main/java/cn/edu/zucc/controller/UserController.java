package cn.edu.zucc.controller;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.exception.WrongPasswordException;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.utils.CryptUtils;
import cn.edu.zucc.utils.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    public  ResultVo<Object> login(@RequestBody LoginVo loginVo, HttpServletResponse hsrp){
        String pwd = null;
        qopUserService.loginErrIf(loginVo);
        if (qopUserService.emailIf(loginVo)){
            pwd = qopUserService.queryByEmail(loginVo.getUserName()).getPassword();
        }else {
            pwd = qopUserService.queryByPhone(loginVo.getUserName()).getPassword();
        }

       WrongPasswordException e = new WrongPasswordException("","");
        return ResponseBuilder.buildErrorResponse(e);
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultVo<QopUser> register(@RequestBody RegisterVo registerVo){
        return ResponseBuilder.buildSuccessResponse(qopUserService.addUser(qopUserService.RegistervoToQopUser(registerVo)));
    }

}
