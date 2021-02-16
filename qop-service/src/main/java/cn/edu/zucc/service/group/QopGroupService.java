package cn.edu.zucc.service.group;

import cn.edu.zucc.group.vo.GroupInfoVo;
import cn.edu.zucc.group.vo.GroupMemberInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Bruce
 * @since 02-13-2021
 */
public interface QopGroupService {
    GroupInfoVo createOneGroup(GroupInfoVo groupInfoVo, Long userId);

    void updateGroupInfo(GroupInfoVo groupInfoVo, Long userId);

    void deleteGroup(Long groupId, Long userId);

    Page<GroupMemberInfoVo> getGroupMembers(Long groupId, Long userId, Pageable pageable);
}
