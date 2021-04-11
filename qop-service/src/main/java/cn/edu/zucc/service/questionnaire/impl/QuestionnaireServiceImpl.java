package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.QuestionnaireStatus;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
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
import java.util.ArrayList;
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
    public QopQuestionnaireVo getQuestionnaire(String id, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findById(id).orElseThrow(() -> {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        });
        QopQuestionnaireVo qopQuestionnaireVo = new QopQuestionnaireVo();
        BeanUtils.copyProperties(questionnaire, qopQuestionnaireVo);
        Integer status = questionnaire.getStatus();
        if (QuestionnaireStatus.PUBLIC.getCode().equals(status)) {
            return qopQuestionnaireVo;
        } else if (QuestionnaireStatus.GROUP_OPEN.getCode().equals(status)) {
            // TODO
            return null;
        } else if (QuestionnaireStatus.DELETED.getCode().equals(status) || QuestionnaireStatus.DRAFT.getCode().equals(status)) {
            if (questionnaire.getUid().equals(uid)) {
                return qopQuestionnaireVo;
            }
            return null;
        }
        return null;
    }

    @Override
    public Page<QuestionnaireInfoVo> getMyQuestionnaires(Long uid, Pageable pageable) {
        Page<QopQuestionnaire> allByUid = qopQuestionnaireRepository.findAllByUidAndStatusIsNot(uid, QuestionnaireStatus.DELETED.getCode(), pageable);
        if (allByUid.getTotalElements() > 0) {
            return new PageImpl<>(ListUtils.copyListProperties(allByUid.getContent(), QuestionnaireInfoVo::new), pageable, allByUid.getTotalElements());
        }
        return new PageImpl<>(new ArrayList<>(), pageable, allByUid.getTotalElements());
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
    public QuestionnaireInfoVo publishQuestionnaire(String id, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(id, uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        questionnaire.setStatus(QuestionnaireStatus.PUBLIC.getCode());
        questionnaire.setPublishTime(new Date());
        qopQuestionnaireRepository.save(questionnaire);
        return null;
    }

    @Override
    public void deleteQuestionnaire(String id, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(id, uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        questionnaire.setStatus(QuestionnaireStatus.DELETED.getCode());
        questionnaire.setPublishTime(null);
        questionnaire.setDeleteTime(new Date());
        qopQuestionnaireRepository.save(questionnaire);
    }

    @Override
    public void updateQuestionnaireInfo(QuestionnaireInfoVo questionnaireInfoVo, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(questionnaireInfoVo.getId(), uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        questionnaire.setTitle(questionnaireInfoVo.getTitle());
        questionnaire.setDescription(questionnaire.getDescription());
        qopQuestionnaireRepository.save(questionnaire);
    }

    @Override
    public void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid) {
        QopQuestionnaire questionnaire = qopQuestionnaireRepository.findByIdAndUid(qopQuestionnaireVo.getId(), uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        questionnaire.setTitle(qopQuestionnaireVo.getTitle());
        questionnaire.setDescription(questionnaire.getDescription());
        questionnaire.setQuestionNum(qopQuestionnaireVo.getQuestions().size());
        questionnaire.setQuestions(qopQuestionnaireVo.getQuestions());
        qopQuestionnaireRepository.save(questionnaire);
    }
}
