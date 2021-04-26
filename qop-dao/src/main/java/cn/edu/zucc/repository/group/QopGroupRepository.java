package cn.edu.zucc.repository.group;

import cn.edu.zucc.group.po.QopGroup;
import cn.edu.zucc.group.vo.GroupInfoVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bruce
 * @since 03-13-2021
 */
@Repository
public interface QopGroupRepository extends JpaRepository<QopGroup, Long> {
    @Modifying
    @Query(value = "update QopGroup set deleted='Y' where id=:groupId")
    void deleteQopGroup(@Param("groupId") Long groupId);

    @Query(value = "select g.id, g.name group_name, g.introduction, g.img" +
            " from qop_group g,qop_group_member m" +
            " where g.id=m.group_id and m.user_id=:userId" +
            " order by m.join_date",
            nativeQuery = true)
    List<GroupInfoVo> findMyGroupsByUserId(@Param("userId") Long userId);
}
