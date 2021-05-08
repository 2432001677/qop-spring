package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.GroupRole;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.exception.UnAuthorizedException;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.service.group.impl.QopGroupServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "小组管理")
@RefreshScope
@RestController
@RequestMapping("manage")
public class GroupManagementController {
    @Resource
    private QopGroupServiceImpl qopGroupService;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Value("${jwt.issuer}")
    private String issuer;

    @ApiOperation("新建小组")
    @PostMapping
    public ResultVo<GroupInfoVo> createNewGroup(@RequestHeader("Authorization") String token, @RequestBody GroupInfoVo groupInfoVo) {
        return ResponseBuilder.buildSuccessResponse(qopGroupService.createOneGroup(groupInfoVo, TokenUtils.getUserId(token, tokenSecret, issuer)));
    }

    @ApiOperation("修改小组信息")
    @PostMapping("/update")
    public ResultVo<GroupInfoVo> modifyGroupInfo(@RequestHeader("Authorization") String token, @RequestBody GroupInfoVo groupInfoVo) {
        Long userId = TokenUtils.getUserId(token, tokenSecret, issuer);
        if (groupInfoVo == null || groupInfoVo.getId() == null || StringUtils.isBlank(groupInfoVo.getGroupName()) || userId == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        qopGroupService.checkInMemberInGroup(ResponseMsg.GROUP_NOT_FOUND, groupInfoVo.getId().longValue(), userId);
        qopGroupService.updateGroupInfo(groupInfoVo);
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("删除组成员")
    @PostMapping("/delete-member")
    public ResultVo<Void> deleteGroupMembers() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("解散小组")
    @GetMapping("/delete")
    public ResultVo<Void> deleteGroup(@RequestHeader("Authorization") String token, @RequestParam(value = "groupId") Long groupId) {
        Long userId = TokenUtils.getUserId(token, tokenSecret, issuer);
        if (groupId == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        var qopGroupMember = qopGroupService.checkInMemberInGroup(ResponseMsg.GROUP_NOT_FOUND, groupId, userId);
        if (!StringUtils.equals(qopGroupMember.getUserRole(), GroupRole.GROUP_OWNER.getCode())) {
            throw new UnAuthorizedException(ResponseMsg.NOT_GROUP_OWNER);
        }
        qopGroupService.deleteGroup(groupId, userId);
        return ResponseBuilder.buildSuccessResponse();
    }
}
