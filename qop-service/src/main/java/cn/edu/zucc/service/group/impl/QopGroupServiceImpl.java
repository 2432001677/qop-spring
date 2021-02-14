package cn.edu.zucc.service.group.impl;

import cn.edu.zucc.constant.CommonConstants;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.GroupRole;
import cn.edu.zucc.exception.FormInfoException;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.exception.UnAuthorizedException;
import cn.edu.zucc.group.po.QopGroup;
import cn.edu.zucc.group.po.QopGroupMember;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.repository.group.QopGroupMemberRepository;
import cn.edu.zucc.repository.group.QopGroupRepository;
import cn.edu.zucc.service.group.QopGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GroupInfoVo createOneGroup(GroupInfoVo groupInfoVo, Long userId) {
        if (groupInfoVo == null || StringUtils.isBlank(groupInfoVo.getGroupName()) || userId == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        Date nowDate = new Date();

        QopGroup qopGroup = new QopGroup();
        qopGroup.setName(groupInfoVo.getGroupName());
        qopGroup.setIntroduction(groupInfoVo.getIntroduction());
        qopGroup.setImg(groupInfoVo.getImg());
        qopGroup.setDeleted(CommonConstants.N);
        qopGroup.setCreateDate(nowDate);
        QopGroup res = qopGroupRepository.save(qopGroup);

        QopGroupMember qopGroupMember = new QopGroupMember();
        qopGroupMember.setGroupId(res.getId());
        qopGroupMember.setUserId(userId);
        qopGroupMember.setUserRole(GroupRole.GROUP_OWNER.getCode());
        qopGroupMember.setJoinDate(nowDate);
        qopGroupMemberRepository.save(qopGroupMember);

        groupInfoVo.setId(res.getId());
        return groupInfoVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGroup(Long groupId, Long userId) {
        if (groupId == null) {
            throw new FormInfoException(ResponseMsg.REQUEST_INFO_ERROR);
        }
        QopGroupMember qopGroupMember = qopGroupMemberRepository.findQopGroupMemberByGroupIdAndUserId(groupId, userId);
        if (qopGroupMember == null) {
            throw new SourceNotFoundException(ResponseMsg.GROUP_NOT_FOUND);
        }
        if (!StringUtils.equals(qopGroupMember.getUserRole(), GroupRole.GROUP_OWNER.getCode())) {
            throw new UnAuthorizedException(ResponseMsg.NOT_GROUP_OWNER);
        }
        qopGroupMemberRepository.deleteByGroupId(groupId);
        qopGroupRepository.deleteQopGroup(groupId);
    }
}
