package cn.edu.zucc.repository.group;

import cn.edu.zucc.group.po.QopGroupMember;
import cn.edu.zucc.group.vo.GroupMemberInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Bruce
 * @since 02-14-2021
 */
@Repository
public interface QopGroupMemberRepository extends JpaRepository<QopGroupMember, Long> {
    QopGroupMember findQopGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    void deleteByGroupId(Long groupId);

    @Query(value = "select qop_user.id user_id,qop_user.nick_name,m.user_role,m.join_date" +
            " from qop_user,qop_group_member m" +
            " where m.user_id=qop_user.id and m.group_id=:groupId" +
            " order by field(m.user_role,'01','02','03')",
            nativeQuery = true)
    Page<GroupMemberInfoVo> findGroupMemberInfoVoPageList(@Param("groupId") Long groupId, Pageable pageable);
}
