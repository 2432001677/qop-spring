package cn.edu.zucc.repository.group;

import cn.edu.zucc.group.po.QopGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bruce
 * @since 02-14-2021
 */
@Repository
public interface QopGroupMemberRepository extends JpaRepository<QopGroupMember, Long> {
    QopGroupMember findQopGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    void deleteByGroupId(Long groupId);
}
