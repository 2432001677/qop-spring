package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.questionnaire.po.QopTrashQuestionnaire;
import cn.edu.zucc.service.questionnaire.TrashQuestionnaireService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Bruce
 * @since 03-11-2021
 */
@Service
public class TrashQuestionnaireServiceImpl implements TrashQuestionnaireService {
    @Override
    public Page<QopTrashQuestionnaire> queryTrashPageQuestionnaire(int uid, int page, int size) {
        return null;
    }

    @Override
    public void clear(int uid) {

    }
}
