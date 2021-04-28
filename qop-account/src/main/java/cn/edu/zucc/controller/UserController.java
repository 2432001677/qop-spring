package cn.edu.zucc.controller;

import cn.edu.zucc.account.vo.*;
import cn.edu.zucc.common.vo.ResultPageVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import cn.edu.zucc.service.account.impl.QopUserServiceImpl;
import cn.edu.zucc.service.questionnaire.impl.QuestionnaireServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenProviderUtils;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
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
    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Resource
    private QopUserServiceImpl qopUserService;

    @Resource
    private QuestionnaireServiceImpl questionnaireService;

    @ApiOperation("login")
    @PostMapping("/login")
    public ResultVo<AccountProfilesVo> login(@RequestBody LoginVo loginVo, HttpServletResponse response) {
        if (loginVo == null || StringUtils.isBlank(loginVo.getUserName()) || StringUtils.isBlank(loginVo.getPassword())) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        var qopUser = qopUserService.login(loginVo);
        response.setHeader("Authorization", TokenProviderUtils.sign(qopUser.getId(), tokenSecret, issuer));
        var accountProfilesVo = new AccountProfilesVo();
        BeanUtils.copyProperties(qopUser, accountProfilesVo);
        return ResponseBuilder.buildSuccessResponse(accountProfilesVo);
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

    @ApiOperation("我的问卷")
    @GetMapping("/my-questionnaire")
    public ResultPageVo<QuestionnaireInfoVo> getMyQuestionnaires(@RequestHeader("Authorization") String token,
                                                                 @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                                                 @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return ResponseBuilder.buildSuccessPageableResponse(questionnaireService.getMyQuestionnaires(TokenUtils.getUserId(token, tokenSecret, issuer), PageRequest.of(page - 1, size)));
    }

    @ApiOperation("获取头像")
    @GetMapping("/icon")
    public ResultVo<Void> previewIcon() {
        //todo
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("更新头像")
    @PostMapping("/icon")
    public ResultVo<Void> updateIcon() {
        //todo
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("获取个人资料")
    @GetMapping("/profiles")
    public ResultVo<AccountProfilesVo> getMyProfiles(@RequestHeader("Authorization") String token) {
        return ResponseBuilder.buildSuccessResponse(qopUserService.getProfilesById(TokenUtils.getUserId(token, tokenSecret, issuer)));
    }

    @ApiOperation("更新个人资料")
    @PostMapping("/profiles")
    public ResultVo<Void> updateMyProfiles(@RequestHeader("Authorization") String token,
                                           @RequestBody AccountProfilesVo accountProfilesVo) {
        if (accountProfilesVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        qopUserService.updateProfilesById(accountProfilesVo, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("获得我的通知")
    @GetMapping("/notification")
    public ResultVo<List<NotificationVo>> getMyNotifications(@RequestHeader("Authorization") String token) {
        return ResponseBuilder.buildSuccessResponse(qopUserService.getNotificationByUserId(TokenUtils.getUserId(token, tokenSecret, issuer)));
    }

    @ApiOperation("更新密码")
    @PostMapping("/password")
    public ResultVo<Void> updateMyPassword(@RequestBody ChangePasswordVo changePasswordVo) {
        if (changePasswordVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        qopUserService.changePassword(changePasswordVo);
        return ResponseBuilder.buildSuccessResponse();
    }
}
