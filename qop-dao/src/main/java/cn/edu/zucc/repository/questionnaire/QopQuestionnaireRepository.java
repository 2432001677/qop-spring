package cn.edu.zucc.repository.questionnaire;

import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QopQuestionnaireRepository extends MongoRepository<QopQuestionnaire, String> {
}
