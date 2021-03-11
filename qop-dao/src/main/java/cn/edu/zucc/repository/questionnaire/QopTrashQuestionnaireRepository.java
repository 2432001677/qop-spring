package cn.edu.zucc.repository.questionnaire;

import cn.edu.zucc.questionnaire.po.QopTrashQuestionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QopTrashQuestionnaireRepository extends MongoRepository<QopTrashQuestionnaire, String> {
}
