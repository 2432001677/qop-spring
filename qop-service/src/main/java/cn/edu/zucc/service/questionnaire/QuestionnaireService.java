package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.questionnaire.po.QopTrashQuestionnaire;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import org.springframework.data.domain.Page;

public interface QuestionnaireService {
    QopQuestionnaire getQuestionnaire(String id);

    QopQuestionnaire addQuestionnaire(QopQuestionnaireVo beanQuestionnaire);

    void deleteQuestionnaire(QopQuestionnaireVo beanQuestionnaire);

    QopQuestionnaire updateQuestionnaire(QopQuestionnaireVo beanQuestionnaire);

    Page<QopQuestionnaire> pageQuestionnaire(int uid, int page, int size);

    /**
     * 移入垃圾箱
     *
     * @param trashQuestionnaire
     * @return
     */
    QopTrashQuestionnaire addToTrash(QopTrashQuestionnaire trashQuestionnaire);
}
