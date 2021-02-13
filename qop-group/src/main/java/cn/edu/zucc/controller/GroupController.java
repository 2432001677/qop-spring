package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.group.po.QopGroup;
import cn.edu.zucc.service.group.impl.QopGroupServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "小组")
@RefreshScope
@RestController
@RequestMapping("group")
public class GroupController {
    @Resource
    private QopGroupServiceImpl qopGroupService;

    @ApiOperation("测试MySql")
    @GetMapping
    public ResultVo<List<QopGroup>> getAllUsers() {
        return ResponseBuilder.buildSuccessResponse(qopGroupService.queryAll());
    }
}
