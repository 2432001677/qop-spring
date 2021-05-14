package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.questionnaire.vo.PublishQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionnaireService {
    QopQuestionnaireVo getQuestionnaire(String qid, Long userId);

    QopQuestionnaireVo getGroupQuestionnaire(String qid, Long groupId, Long userId);

    Page<QuestionnaireInfoVo> getMyQuestionnaires(Long userId, Pageable pageable);

    List<QuestionnaireInfoVo> getQuestionnaireByGroupId(Long groupId, Long userId);

    void addQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long userId);

    void publishQuestionnaire(PublishQuestionnaireVo publishQuestionnaireVo, QopQuestionnaire qopQuestionnaire);

    void deleteQuestionnaire(QopQuestionnaire questionnaire);

    void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, QopQuestionnaire questionnaire);

    QopQuestionnaire checkQuestionnaireOwner(String qid, Long userId);
}
