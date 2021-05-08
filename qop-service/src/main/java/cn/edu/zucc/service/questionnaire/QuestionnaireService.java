package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.vo.PublishQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionnaireService {
    QopQuestionnaireVo getQuestionnaire(String id, Long uid);

    QopQuestionnaireVo getGroupQuestionnaire(String qid, Long groupId, Long uid);

    Page<QuestionnaireInfoVo> getMyQuestionnaires(Long uid, Pageable pageable);

    List<QuestionnaireInfoVo> getQuestionnaireByGroupId(Long groupId, Long uid);

    void addQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid);

    void publishQuestionnaire(PublishQuestionnaireVo publishQuestionnaireVo, Long uid);

    void deleteQuestionnaire(String id, Long uid);

    void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid);
}
