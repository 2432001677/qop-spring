package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.utils.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "问卷")
@RefreshScope
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @ApiOperation("我的问卷")
    @GetMapping
    public ResultVo<Void> getMyQuestionnaires() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("创建问卷")
    @PostMapping
    public ResultVo<Void> createQuestionnaire() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("更新问卷内容")
    @PostMapping("/questions")
    public ResultVo<Void> updateQuestionnaireQuestions() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("更新问卷信息")
    @PostMapping("/update")
    public ResultVo<Void> updateQuestionnaireInfo() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("发布问卷")
    @PostMapping("/publish")
    public ResultVo<Void> publishQuestionnaire() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("删除问卷")
    @PostMapping("/delete")
    public ResultVo<Void> deleteQuestionnaire() {
        return ResponseBuilder.buildSuccessResponse();
    }
}
