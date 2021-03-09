package cn.edu.zucc.controller;

import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "分析")
@RefreshScope
@RestController
@RequestMapping("analysis")
public class AnalysisController {
}
