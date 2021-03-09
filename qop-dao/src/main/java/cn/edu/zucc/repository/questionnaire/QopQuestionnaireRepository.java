package cn.edu.zucc.repository.questionnaire;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QopQuestionnaireRepository extends MongoRepository<QopQuestionnaireRepository, String> {
}
