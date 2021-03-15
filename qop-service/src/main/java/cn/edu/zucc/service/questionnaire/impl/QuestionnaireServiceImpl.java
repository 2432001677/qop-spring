package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.enums.QuestionnaireStatus;
import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.questionnaire.vo.PublishQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import cn.edu.zucc.repository.questionnaire.QopQuestionnaireRepository;
import cn.edu.zucc.service.questionnaire.QuestionnaireService;
import cn.edu.zucc.utils.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Resource
    private QopQuestionnaireRepository qopQuestionnaireRepository;

    @Override
    public QopQuestionnaireVo getQuestionnaire(String id, Integer status) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndStatus(id, status);
        QopQuestionnaireVo qopQuestionnaireVo = new QopQuestionnaireVo();
        BeanUtils.copyProperties(questionnaire, qopQuestionnaireVo);
        return qopQuestionnaireVo;
    }

    @Override
    public Page<QuestionnaireInfoVo> getMyQuestionnaires(Long uid, Pageable pageable) {
        Page<QopQuestionnaire> allByUid = qopQuestionnaireRepository.findAllByUidAndStatusIsNot(uid, QuestionnaireStatus.DELETED.getCode(), pageable);
        if (allByUid.getTotalElements() > 0) {
            return new PageImpl<>(ListUtils.copyListProperties(allByUid.getContent(), QuestionnaireInfoVo::new), pageable, allByUid.getTotalElements());
        }
        return null;
    }

    @Override
    public void addQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid) {
        QopQuestionnaire questionnaire = new QopQuestionnaire();
        BeanUtils.copyProperties(qopQuestionnaireVo, questionnaire);
        questionnaire.setUid(uid);
        questionnaire.setQuestionNum(qopQuestionnaireVo.getQuestions().size());
        questionnaire.setCreateTime(new Date());
        qopQuestionnaireRepository.insert(questionnaire);
    }

    @Override
    public QuestionnaireInfoVo publishQuestionnaire(PublishQuestionnaireVo publishQuestionnaireVo, Long uid) {
        return null;
    }

    @Override
    public void deleteQuestionnaire(String id, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(id, uid);
        questionnaire.setStatus(QuestionnaireStatus.DELETED.getCode());
        questionnaire.setDeleteTime(new Date());
    }

    @Override
    public void updateQuestionnaireInfo(QuestionnaireInfoVo questionnaireInfoVo, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(questionnaireInfoVo.getId(), uid);
        questionnaire.setTitle(questionnaireInfoVo.getTitle());
        questionnaire.setDescription(questionnaire.getDescription());
    }

    @Override
    public void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(qopQuestionnaireVo.getId(), uid);
        questionnaire.setQuestions(qopQuestionnaireVo.getQuestions());
        qopQuestionnaireRepository.save(questionnaire);
    }
}
