package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultPageVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.group.vo.GroupMemberInfoVo;
import cn.edu.zucc.service.group.impl.QopGroupServiceImpl;
import cn.edu.zucc.utils.ResponseBuilder;
import cn.edu.zucc.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Bruce
 * @since 02-15-2021
 */
@Api("小组基本功能")
@RefreshScope
@RestController
@RequestMapping("/group")
public class GroupInfoController {
    @Resource
    private QopGroupServiceImpl qopGroupService;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Value("${jwt.issuer}")
    private String issuer;

    @ApiOperation("显示加入的组")
    @GetMapping
    public ResultPageVo<GroupInfoVo> getMyGroups(@RequestHeader("Authorization") String token,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return ResponseBuilder.buildSuccessPageableResponse(qopGroupService.getGroupsById(TokenUtils.getUserId(token, tokenSecret, issuer), PageRequest.of(page - 1, size)));
    }

    @ApiOperation("显示组成员信息")
    @GetMapping("/members")
    public ResultPageVo<GroupMemberInfoVo> getGroupMembersInfo(@RequestHeader("Authorization") String token,
                                                               @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                                               @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                                               @RequestParam("groupId") Long groupId) {
        return ResponseBuilder.buildSuccessPageableResponse(qopGroupService.getGroupMembers(groupId, TokenUtils.getUserId(token, tokenSecret, issuer), PageRequest.of(page - 1, size)));
    }

    @ApiOperation("邀请加入组")
    @PostMapping("/invite")
    public ResultVo<Void> inviteToGroup() {
        //todo
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("加入组")
    @PostMapping("/join")
    public ResultVo<Void> joinGroup() {
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("离开组")
    @PostMapping("/leave/{groupId}")
    public ResultVo<Void> leaveGroup(@RequestHeader("Authorization") String token, @PathVariable("groupId") Long groupId) {
        if (groupId == null) {
            throw new FormInfoException(ResponseMsg.GROUP_NOT_FOUND);
        }
        qopGroupService.leaveGroup(groupId, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessResponse();
    }
}
