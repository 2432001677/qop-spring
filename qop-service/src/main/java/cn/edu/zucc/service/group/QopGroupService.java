package cn.edu.zucc.service.group;

import cn.edu.zucc.group.po.QopGroupMember;
import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.group.vo.GroupMemberInfoVo;
import cn.edu.zucc.group.vo.InvitationVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Bruce
 * @since 02-13-2021
 */
public interface QopGroupService {
    GroupInfoVo createOneGroup(GroupInfoVo groupInfoVo, Long userId);

    void updateGroupInfo(GroupInfoVo groupInfoVo);

    void deleteGroup(Long groupId, Long userId);

    Page<GroupMemberInfoVo> getGroupMembers(Long groupId, Pageable pageable);

    List<GroupInfoVo> getGroupsById(Long userId);

    void leaveGroup(Long groupId, Long userId);

    void inviteUser(InvitationVo invitationVo, Long userId);

    QopGroupMember checkInMemberInGroup(String errMsg, Long groupId, Long userId);
}
