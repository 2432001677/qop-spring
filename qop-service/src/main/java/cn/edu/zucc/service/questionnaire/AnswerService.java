package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.vo.QopAnswerVo;

public interface AnswerService {
    void answerQuestionnaire(QopAnswerVo qopAnswerVo, Long uid);

    void answerGroupQuestionnaire(Long groupId, QopAnswerVo qopAnswerVo, Long uid);
}
