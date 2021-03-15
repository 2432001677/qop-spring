package cn.edu.zucc.service.questionnaire;

import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import org.springframework.data.domain.Page;

public interface TrashQuestionnaireService {
    /**
     * 垃圾箱分页查看
     *
     * @param uid
     * @param page
     * @param size
     * @return
     */
    Page<QuestionnaireInfoVo> queryTrashPageQuestionnaire(int uid, int page, int size);

    void clear(int uid);
}
