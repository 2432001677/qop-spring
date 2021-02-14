package cn.edu.zucc.repository.group;

import cn.edu.zucc.group.po.QopGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QopGroupRepository extends JpaRepository<QopGroup, Long> {
    @Modifying
    @Query(value = "update QopGroup set deleted='Y' where id=:groupId")
    void deleteQopGroup(@Param("groupId") Long groupId);
}
