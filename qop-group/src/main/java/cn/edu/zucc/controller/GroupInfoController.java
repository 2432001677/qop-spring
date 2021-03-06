package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultPageVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.group.vo.GroupMemberInfoVo;
import cn.edu.zucc.group.vo.InvitationVo;
import cn.edu.zucc.questionnaire.vo.QopAnswerVo;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import cn.edu.zucc.service.group.impl.QopGroupServiceImpl;
import cn.edu.zucc.service.questionnaire.impl.AnswerServiceImpl;
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
import java.util.List;

/**
 * @author Bruce
 * @since 02-15-2021
 */
@Api(tags = "小组基本功能")
@RefreshScope
@RestController
@RequestMapping("/group")
public class GroupInfoController {
    @Resource
    private QopGroupServiceImpl qopGroupService;
    @Resource
    private QuestionnaireServiceImpl questionnaireService;
    @Resource
    private AnswerServiceImpl answerService;

    @Value("${jwt.secret}")
    private String tokenSecret;
    @Value("${jwt.issuer}")
    private String issuer;

    @ApiOperation("显示加入的组")
    @GetMapping
    public ResultVo<List<GroupInfoVo>> getMyGroups(@RequestHeader("Authorization") String token) {
        return ResponseBuilder.buildSuccessResponse(qopGroupService.getGroupsById(TokenUtils.getUserId(token, tokenSecret, issuer)));
    }

    @ApiOperation("显示组成员信息")
    @GetMapping("/members")
    public ResultPageVo<GroupMemberInfoVo> getGroupMembersInfo(@RequestHeader("Authorization") String token,
                                                               @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
                                                               @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                                               @RequestParam("groupId") Long groupId) {
        qopGroupService.checkInMemberInGroup(ResponseMsg.REQUEST_INFO_ERROR, groupId, TokenUtils.getUserId(token, tokenSecret, issuer));
        return ResponseBuilder.buildSuccessPageableResponse(qopGroupService.getGroupMembers(groupId, PageRequest.of(page - 1, size)));
    }

    @ApiOperation("邀请加入组")
    @PostMapping("/invitation")
    public ResultVo<Void> inviteToGroup(@RequestHeader("Authorization") String token, @RequestBody InvitationVo invitationVo) {
        Long userId = TokenUtils.getUserId(token, tokenSecret, issuer);
        if (invitationVo == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        Long groupId = invitationVo.getGroupId();
        if (groupId == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        qopGroupService.checkInMemberInGroup(ResponseMsg.REQUEST_INFO_ERROR, groupId, userId);
        qopGroupService.inviteUser(invitationVo, userId);
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
        Long userId = TokenUtils.getUserId(token, tokenSecret, issuer);
        if (groupId == null) {
            throw new FormInfoException(ResponseMsg.GROUP_NOT_FOUND);
        }
        qopGroupService.checkInMemberInGroup(ResponseMsg.REQUEST_INFO_ERROR, groupId, userId);
        qopGroupService.leaveGroup(groupId, userId);
        return ResponseBuilder.buildSuccessResponse();
    }

    @ApiOperation("获取组内所有问卷")
    @GetMapping("/{groupId}/questionnaires")
    public ResultVo<List<QuestionnaireInfoVo>> getQuestionnairesByGroup(@RequestHeader("Authorization") String token, @PathVariable("groupId") Long groupId) {
        if (groupId == null) {
            throw new FormInfoException(ResponseMsg.GROUP_NOT_FOUND);
        }

        return ResponseBuilder.buildSuccessResponse(questionnaireService.getQuestionnaireByGroupId(groupId, TokenUtils.getUserId(token, tokenSecret, issuer)));
    }

    @ApiOperation("获取组内指定问卷")
    @GetMapping("/{groupId}/questionnaire/{qid}")
    public ResultVo<QopQuestionnaireVo> getGroupQuestionnaire(@RequestHeader("Authorization") String token, @PathVariable("groupId") Long groupId, @PathVariable("qid") String qid) {
        return ResponseBuilder.buildSuccessResponse(questionnaireService.getGroupQuestionnaire(qid, groupId, TokenUtils.getUserId(token, tokenSecret, issuer)));
    }

    @ApiOperation("提交组内问卷")
    @PostMapping("/{groupId}/answer")
    public ResultVo<Void> answerGroupQuestionnaire(@RequestHeader("Authorization") String token, @PathVariable("groupId") Long groupId, @RequestBody QopAnswerVo qopAnswerVo) {
        Long userId = TokenUtils.getUserId(token, tokenSecret, issuer);
        qopGroupService.checkInMemberInGroup(ResponseMsg.REQUEST_INFO_ERROR, groupId, userId);
        answerService.answerGroupQuestionnaire(groupId, qopAnswerVo, userId);
        return ResponseBuilder.buildSuccessResponse();
    }
}
