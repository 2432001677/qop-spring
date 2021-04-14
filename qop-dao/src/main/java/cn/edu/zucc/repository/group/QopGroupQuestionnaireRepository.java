package cn.edu.zucc.repository.group;

import cn.edu.zucc.group.po.QopGroupQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QopGroupQuestionnaireRepository extends JpaRepository<QopGroupQuestionnaire, Long> {
    List<QopGroupQuestionnaire> findAllByGroupIdOrderByPublishDateDesc(Long groupId);
}
