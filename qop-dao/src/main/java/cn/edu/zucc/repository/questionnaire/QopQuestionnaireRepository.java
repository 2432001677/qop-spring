package cn.edu.zucc.repository.questionnaire;

import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QopQuestionnaireRepository extends MongoRepository<QopQuestionnaire, String> {
    Page<QopQuestionnaire> findAllByUidAndStatusIsNot(Long uid, Integer status, Pageable pageable);

    QopQuestionnaire findByIdAndStatusIsNot(String id, Integer status);

    QopQuestionnaire findByIdAndUid(String id, Long uid);
}
