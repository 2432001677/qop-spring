package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.questionnaire.vo.QopAnswerVo;
import cn.edu.zucc.service.questionnaire.impl.AnswerServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Bruce
 * @since 03-12-2021
 */
@Api(tags = "回答")
@RestController
@RefreshScope
@RequestMapping("/answer")
public class AnswerController {
    @Resource
    private AnswerServiceImpl answerService;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @ApiOperation("回答问卷")
    @PostMapping
    public ResultVo<Void> answerQuestionnaire(@RequestHeader("Authorization") String token,
                                              @RequestBody QopAnswerVo qopAnswerVo) {
        if (qopAnswerVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        answerService.answerQuestionnaire(qopAnswerVo, StringUtils.isBlank(token) ? null : TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }
}
