package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.service.group.impl.QopGroupServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "小组管理")
@RestController
@RequestMapping("manage")
public class GroupManagementController {
    @Resource
    private QopGroupServiceImpl qopGroupService;

    @ApiOperation("新建小组")
    @PostMapping
    public ResultVo<GroupInfoVo> createNewGroup(@RequestHeader("Authorization") String token, @RequestBody GroupInfoVo groupInfoVo) {
        return ResponseBuilder.buildSuccessResponse(qopGroupService.createOneGroup(groupInfoVo, TokenUtils.getUserId(token)));
    }

    @ApiOperation("修改小组信息")
    @PostMapping("/update")
    public ResultVo<GroupInfoVo> modifyGroupInfo(@RequestHeader("Authorization") String token, @RequestBody GroupInfoVo groupInfoVo) {
        qopGroupService.updateGroupInfo(groupInfoVo, TokenUtils.getUserId(token));
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("解散小组")
    @GetMapping("/delete")
    public ResultVo<Void> deleteGroup(@RequestHeader("Authorization") String token, @RequestParam(value = "groupId") Long groupId) {
        qopGroupService.deleteGroup(groupId, TokenUtils.getUserId(token));
        return ResponseBuilder.buildSuccessResponse();
    }
}
