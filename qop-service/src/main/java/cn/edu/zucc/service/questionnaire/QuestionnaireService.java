package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireService {
    QopQuestionnaireVo getQuestionnaire(String id, Long uid);

    Page<QuestionnaireInfoVo> getMyQuestionnaires(Long uid, Pageable pageable);

    void addQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid);

    QuestionnaireInfoVo publishQuestionnaire(String id, Long uid);

    void deleteQuestionnaire(String id, Long uid);

    void updateQuestionnaireInfo(QuestionnaireInfoVo questionnaireInfoVo, Long uid);

    void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid);
}
