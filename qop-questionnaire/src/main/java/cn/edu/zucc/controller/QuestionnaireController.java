package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultPageVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.questionnaire.vo.PublishQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import cn.edu.zucc.service.questionnaire.impl.QuestionnaireServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "问卷")
@RefreshScope
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Resource
    private QuestionnaireServiceImpl questionnaireService;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @ApiOperation("我的问卷")
    @GetMapping
    public ResultPageVo<QuestionnaireInfoVo> getMyQuestionnaires(@RequestHeader("Authorization") String token,
                                                                 @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                                                 @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return ResponseBuilder.buildSuccessPageableResponse(questionnaireService.getMyQuestionnaires(TokenUtils.getUserId(token, tokenSecret, issuer), PageRequest.of(page - 1, size)));
    }

    @ApiOperation("创建问卷")
    @PostMapping
    public ResultVo<Void> createQuestionnaire(@RequestHeader("Authorization") String token,
                                              @RequestBody QopQuestionnaireVo qopQuestionnaireVo) {
        if (qopQuestionnaireVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        questionnaireService.addQuestionnaire(qopQuestionnaireVo, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("更新问卷内容")
    @PostMapping("/questions")
    public ResultVo<Void> updateQuestionnaireQuestions(@RequestHeader("Authorization") String token,
                                                       @RequestBody QopQuestionnaireVo qopQuestionnaireVo) {
        if (qopQuestionnaireVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        questionnaireService.updateQuestionnaire(qopQuestionnaireVo, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("更新问卷信息")
    @PostMapping("/update")
    public ResultVo<Void> updateQuestionnaireInfo(@RequestHeader("Authorization") String token,
                                                  @RequestBody QuestionnaireInfoVo questionnaireInfoVo) {
        if (questionnaireInfoVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        questionnaireService.updateQuestionnaireInfo(questionnaireInfoVo, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("发布问卷")
    @PostMapping("/publish")
    public ResultVo<Void> publishQuestionnaire(@RequestHeader("Authorization") String token,
                                               @RequestBody PublishQuestionnaireVo publishQuestionnaireVo) {
        questionnaireService.publishQuestionnaire(publishQuestionnaireVo, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("删除问卷")
    @PostMapping("/delete/{id}")
    public ResultVo<Void> deleteQuestionnaire(@RequestHeader("Authorization") String token,
                                              @PathVariable("id") String id) {
        questionnaireService.deleteQuestionnaire(id, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }
}
