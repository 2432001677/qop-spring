package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.questionnaire.po.QopTrashQuestionnaire;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.service.questionnaire.QuestionnaireService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Override
    public QopQuestionnaire getQuestionnaire(String id) {
        return null;
    }

    @Override
    public QopQuestionnaire addQuestionnaire(QopQuestionnaireVo beanQuestionnaire) {
        return null;
    }

    @Override
    public void deleteQuestionnaire(QopQuestionnaireVo beanQuestionnaire) {

    }

    @Override
    public QopQuestionnaire updateQuestionnaire(QopQuestionnaireVo beanQuestionnaire) {
        return null;
    }

    @Override
    public Page<QopQuestionnaire> pageQuestionnaire(int uid, int page, int size) {
        return null;
    }

    @Override
    public QopTrashQuestionnaire addToTrash(QopTrashQuestionnaire trashQuestionnaire) {
        return null;
    }
}
