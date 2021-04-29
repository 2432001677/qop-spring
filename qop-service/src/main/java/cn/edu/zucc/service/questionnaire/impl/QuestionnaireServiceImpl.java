package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.QuestionnaireStatus;
import cn.edu.zucc.exception.SourceNotFoundException;
import cn.edu.zucc.exception.UnAuthorizedException;
import cn.edu.zucc.group.po.QopGroupQuestionnaire;
import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.questionnaire.vo.PublishQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QopQuestionnaireVo;
import cn.edu.zucc.questionnaire.vo.QuestionnaireInfoVo;
import cn.edu.zucc.repository.group.QopGroupMemberRepository;
import cn.edu.zucc.repository.group.QopGroupQuestionnaireRepository;
import cn.edu.zucc.repository.questionnaire.QopQuestionnaireRepository;
import cn.edu.zucc.service.questionnaire.QuestionnaireService;
import cn.edu.zucc.utils.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private QopQuestionnaireRepository qopQuestionnaireRepository;
    @Resource
    private QopGroupQuestionnaireRepository qopGroupQuestionnaireRepository;
    @Resource
    private QopGroupMemberRepository qopGroupMemberRepository;

    @Override
    public QopQuestionnaireVo getQuestionnaire(String id, Long uid) {
        var questionnaire = qopQuestionnaireRepository.findById(id).orElseThrow(() -> {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        });
        var qopQuestionnaireVo = new QopQuestionnaireVo();
        BeanUtils.copyProperties(questionnaire, qopQuestionnaireVo);
        var answerNum = (int) mongoTemplate.count(new Query(Criteria.where("questionnaire_id").is(id)), "qop_answer");
        qopQuestionnaireVo.setAnswerNum(answerNum);
        var status = questionnaire.getStatus();
        if (QuestionnaireStatus.PUBLIC.getCode().equals(status)) {
            return qopQuestionnaireVo;
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
        var allByUid = qopQuestionnaireRepository.findAllByUidAndStatusIsNot(uid, QuestionnaireStatus.DELETED.getCode(), pageable);
        if (allByUid.getTotalElements() > 0) {
            return new PageImpl<>(ListUtils.copyListProperties(allByUid.getContent(), QuestionnaireInfoVo::new), pageable, allByUid.getTotalElements());
        }
        return new PageImpl<>(new ArrayList<>(), pageable, allByUid.getTotalElements());
    }

    @Override
    public List<QuestionnaireInfoVo> getQuestionnaireByGroupId(Long uid, Long groupId) {
        var qopGroupMember = qopGroupMemberRepository.findQopGroupMemberByGroupIdAndUserId(groupId, uid);
        if (qopGroupMember == null) {
            throw new UnAuthorizedException(ResponseMsg.NOT_IN_GROUP);
        }
        var groupQuestionnaires = qopGroupQuestionnaireRepository.findAllByGroupIdOrderByPublishDateDesc(groupId);
        List<QuestionnaireInfoVo> qopQuestionnaires = new ArrayList<>(20);
        for (QopGroupQuestionnaire questionnaire : groupQuestionnaires) {
            var qopQuestionnaire = qopQuestionnaireRepository.findByIdAndStatusIsNot(questionnaire.getQuestionnaireId(), QuestionnaireStatus.DELETED.getCode());
            if (qopQuestionnaire != null) {
                var questionnaireInfoVo = new QuestionnaireInfoVo();
                BeanUtils.copyProperties(qopQuestionnaire, questionnaireInfoVo);
                questionnaireInfoVo.setPublishTime(questionnaire.getPublishDate());
                qopQuestionnaires.add(questionnaireInfoVo);
            }
        }
        return qopQuestionnaires;
    }

    @Override
    public void addQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid) {
        var questionnaire = new QopQuestionnaire();
        BeanUtils.copyProperties(qopQuestionnaireVo, questionnaire);
        questionnaire.setUid(uid);
        questionnaire.setQuestionNum(qopQuestionnaireVo.getQuestions().size());
        questionnaire.setCreateTime(new Date());
        qopQuestionnaireRepository.insert(questionnaire);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishQuestionnaire(PublishQuestionnaireVo publishQuestionnaireVo, Long uid) {
        var questionnaire = qopQuestionnaireRepository.findByIdAndUid(publishQuestionnaireVo.getQid(), uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        var now = new Date();
        if (publishQuestionnaireVo.getGroupId() != null) {
            var qopGroupQuestionnaires = new QopGroupQuestionnaire();
            qopGroupQuestionnaires.setQuestionnaireId(publishQuestionnaireVo.getQid());
            qopGroupQuestionnaires.setPublishDate(now);
            qopGroupQuestionnaires.setGroupId(publishQuestionnaireVo.getGroupId());
            qopGroupQuestionnaireRepository.save(qopGroupQuestionnaires);
        }
        if (Boolean.TRUE.equals(publishQuestionnaireVo.getOpen()) && !QuestionnaireStatus.PUBLIC.getCode().equals(questionnaire.getStatus())) {
            questionnaire.setStatus(QuestionnaireStatus.PUBLIC.getCode());
            questionnaire.setPublishTime(now);
            qopQuestionnaireRepository.save(questionnaire);
        }
        if (Boolean.FALSE.equals(publishQuestionnaireVo.getOpen()) && QuestionnaireStatus.PUBLIC.getCode().equals(questionnaire.getStatus())) {
            questionnaire.setStatus(QuestionnaireStatus.DRAFT.getCode());
            questionnaire.setPublishTime(null);
            qopQuestionnaireRepository.save(questionnaire);
        }
    }

    @Override
    public void deleteQuestionnaire(String id, Long uid) {
        var questionnaire = qopQuestionnaireRepository.findByIdAndUid(id, uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        questionnaire.setStatus(QuestionnaireStatus.DELETED.getCode());
        questionnaire.setPublishTime(null);
        questionnaire.setDeleteTime(new Date());
        qopQuestionnaireRepository.save(questionnaire);
    }

    @Override
    public void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long uid) {
        var questionnaire = qopQuestionnaireRepository.findByIdAndUid(qopQuestionnaireVo.getId(), uid);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        questionnaire.setTitle(qopQuestionnaireVo.getTitle());
        questionnaire.setDescription(questionnaire.getDescription());
        questionnaire.setQuestionNum(qopQuestionnaireVo.getQuestions().size());
        questionnaire.setQuestions(qopQuestionnaireVo.getQuestions());
        questionnaire.setScoringMode(qopQuestionnaireVo.getScoringMode());
        qopQuestionnaireRepository.save(questionnaire);
    }
}
