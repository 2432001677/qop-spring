package cn.edu.zucc.service.group;

import cn.edu.zucc.group.vo.GroupInfoVo;

/**
 * @author Bruce
 * @since 02-13-2021
 */
public interface QopGroupService {
    GroupInfoVo createOneGroup(GroupInfoVo groupInfoVo, Long userId);

    void deleteGroup(Long groupId, Long userId);
}
