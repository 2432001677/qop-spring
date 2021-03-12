package cn.edu.zucc.controller;

import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bruce
 * @since 03-12-2021
 */
@Api(tags = "回答")
@RestController
@RefreshScope
@RequestMapping("/answer")
public class AnswerController {
}
