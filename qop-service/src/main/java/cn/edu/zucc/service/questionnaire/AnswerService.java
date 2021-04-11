package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.vo.QopAnswerVo;

public interface AnswerService {
    void answerQuestionnaireByUidAndQid(QopAnswerVo qopAnswerVo, Long uid);
}
