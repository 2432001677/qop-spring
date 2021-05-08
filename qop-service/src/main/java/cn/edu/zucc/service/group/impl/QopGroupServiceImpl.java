package cn.edu.zucc.service.group.impl;

import cn.edu.zucc.account.po.QopNotification;
import cn.edu.zucc.account.po.QopUser;
import cn.edu.zucc.constant.CommonConstants;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.GroupRole;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.group.po.InvitationInfo;
import cn.edu.zucc.group.po.QopGroup;
import cn.edu.zucc.group.po.QopGroupMember;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.group.vo.GroupMemberInfoVo;
import cn.edu.zucc.group.vo.InvitationVo;
import cn.edu.zucc.repository.account.QopUserRepository;
import cn.edu.zucc.repository.group.QopGroupMemberRepository;
import cn.edu.zucc.repository.group.QopGroupRepository;
import cn.edu.zucc.repository.group.QopNotificationRepository;
import cn.edu.zucc.service.group.QopGroupService;
import cn.edu.zucc.utils.FormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Bruce
 * @since 02-13-2021
 */
@Service
public class QopGroupServiceImpl implements QopGroupService {
    @Resource
    private QopGroupRepository qopGroupRepository;
    @Resource
    private QopGroupMemberRepository qopGroupMemberRepository;
    @Resource
    private QopUserRepository qopUserRepository;
    @Resource
    private QopNotificationRepository qopNotificationRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GroupInfoVo createOneGroup(GroupInfoVo groupInfoVo, Long userId) {
        if (groupInfoVo == null || StringUtils.isBlank(groupInfoVo.getGroupName()) || userId == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        var nowDate = new Date();

        var qopGroup = new QopGroup();
        qopGroup.setName(groupInfoVo.getGroupName());
        qopGroup.setIntroduction(groupInfoVo.getIntroduction());
        qopGroup.setImg(groupInfoVo.getImg());
        qopGroup.setDeleted(CommonConstants.N);
        qopGroup.setCreateDate(nowDate);
        var res = qopGroupRepository.save(qopGroup);

        var qopGroupMember = new QopGroupMember();
        qopGroupMember.setGroupId(res.getId());
        qopGroupMember.setUserId(userId);
        qopGroupMember.setUserRole(GroupRole.GROUP_OWNER.getCode());
        qopGroupMember.setJoinDate(nowDate);
        qopGroupMemberRepository.save(qopGroupMember);

        groupInfoVo.setId(new BigInteger(res.getId().toString()));
        return groupInfoVo;
    }

    @Override
    public void updateGroupInfo(GroupInfoVo groupInfoVo) {
        var qopGroup = new QopGroup();
        qopGroup.setName(groupInfoVo.getGroupName());
        qopGroup.setIntroduction(groupInfoVo.getIntroduction());
        qopGroup.setImg(groupInfoVo.getImg());
        qopGroupRepository.save(qopGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGroup(Long groupId, Long userId) {
        qopGroupMemberRepository.deleteByGroupId(groupId);
        qopGroupRepository.deleteQopGroup(groupId);
    }

    @Override
    public Page<GroupMemberInfoVo> getGroupMembers(Long groupId, Pageable pageable) {
        return qopGroupMemberRepository.findGroupMemberInfoVoPageList(groupId, pageable);
    }

    @Override
    public List<GroupInfoVo> getGroupsById(Long userId) {
        return qopGroupRepository.findMyGroupsByUserId(userId);
    }

    @Override
    public void leaveGroup(Long groupId, Long userId) {
        qopGroupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public void inviteUser(InvitationVo invitationVo, Long userId) {
        var userName = invitationVo.getUserName();
        QopUser qopUser;
        if (FormatUtils.isEmail(userName)) {
            qopUser = qopUserRepository.getByEmail(userName);
        } else if (FormatUtils.isPhoneNumber(userName)) {
            qopUser = qopUserRepository.getByPhoneNumber(userName);
        } else {
            throw new FormInfoException(ResponseMsg.NOT_FOUND_USER);
        }
        if (qopUser == null) {
            throw new SourceNotFoundException(ResponseMsg.NOT_FOUND_USER);
        }
        var qopNotification = new QopNotification();
        qopNotification.setUid(qopUser.getId());
        qopNotification.setType(CommonConstants.INVITATION_NOTIFICATION);
        qopNotification.setCreateTime(new Date());
        var invitationInfo = new InvitationInfo();
        invitationInfo.setInviterId(userId);
        invitationInfo.setGroupId(invitationVo.getGroupId());
        qopNotification.setInfo(invitationInfo);

        qopNotificationRepository.save(qopNotification);
    }

    @Override
    public QopGroupMember checkInMemberInGroup(String errMsg, Long groupId, Long userId) {
        var qopGroupMember = qopGroupMemberRepository.findQopGroupMemberByGroupIdAndUserId(groupId, userId);
        if (qopGroupMember == null) {
            throw new FormInfoException(errMsg);
        }
        return qopGroupMember;
    }
}
