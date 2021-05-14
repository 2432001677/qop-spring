package cn.edu.zucc.controller;

import cn.edu.zucc.analysis.vo.AnalysisResult;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.service.analysis.impl.AnalysisServiceImpl;
import cn.edu.zucc.service.questionnaire.impl.QuestionnaireServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "分析")
@RefreshScope
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    private AnalysisServiceImpl analysisService;
    @Resource
    private QuestionnaireServiceImpl questionnaireService;
    @Value("${jwt.secret}")
    private String tokenSecret;
    @Value("${jwt.issuer}")
    private String issuer;

    @ApiOperation("分析结果")
    @GetMapping("/{qid}")
    public ResultVo<AnalysisResult> getAnalysisResult(@PathVariable("qid") String qid, @RequestHeader("Authorization") String token) {
        return ResponseBuilder.buildSuccessResponse(analysisService.getAnalysis(questionnaireService.checkQuestionnaireOwner(qid, TokenUtils.getUserId(token, tokenSecret, issuer))));
    }
}
