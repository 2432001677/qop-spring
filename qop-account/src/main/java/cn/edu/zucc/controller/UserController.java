package cn.edu.zucc.controller;

import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.account.vo.LoginVo;
import cn.edu.zucc.account.vo.RegisterVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.exception.BaseException;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.utils.CryptUtils;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public  <T> ResultVo<Object> login(@RequestBody LoginVo lv, HttpServletResponse hsrp){
        String pwd = null;

        if (lv.getUserName().matches("@")){
            pwd = qopUserService.queryByEmail(lv.getUserName()).getPassword();
        }else if(lv.getUserName()!=null){
            pwd = qopUserService.queryByPhone(lv.getUserName()).getPassword();
        }

        if(pwd==null){
            return ResponseBuilder.buildErrorResponse(new BaseException("无该用户",""));
        }

        if(!CryptUtils.matchAccountPasswd(pwd,lv.getPassword())){
            return ResponseBuilder.buildErrorResponse(new BaseException("登陆失败",""));
        }
        log.info("login");
        hsrp.addHeader("Authorization",TokenUtils.sign(lv));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultVo<QopUser> register(@RequestBody RegisterVo registerVo){
        registerVo.setPassword(CryptUtils.cryptAccountPasswd(registerVo.getPassword()));
        QopUser qopUser = new QopUser();
        BeanUtils.copyProperties(registerVo,qopUser);


        return ResponseBuilder.buildSuccessResponse(qopUserService.addUser(qopUser));
    }

}
