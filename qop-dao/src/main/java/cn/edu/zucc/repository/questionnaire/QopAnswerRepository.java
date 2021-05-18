package cn.edu.zucc.repository.questionnaire;

import cn.edu.zucc.questionnaire.po.QopAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QopAnswerRepository extends MongoRepository<QopAnswer, String> {
    Integer countByQuestionnaireId(String questionnaireId);
}
