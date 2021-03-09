package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "问卷")
@RefreshScope
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @ApiOperation("测试MongoDB")
    @GetMapping
    public ResultVo<Void> getAllUsers() {
        return null;
    }
}
