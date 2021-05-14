package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.enums.QuestionnaireStatus;
import cn.edu.zucc.exception.FormInfoException;
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
    public QopQuestionnaireVo getQuestionnaire(String qid, Long userId) {
        var questionnaire = qopQuestionnaireRepository.findById(qid).orElseThrow(() -> {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        });
        var qopQuestionnaireVo = new QopQuestionnaireVo();
        BeanUtils.copyProperties(questionnaire, qopQuestionnaireVo);
        var answerNum = (int) mongoTemplate.count(new Query(Criteria.where("questionnaire_id").is(qid)), "qop_answer");
        qopQuestionnaireVo.setAnswerNum(answerNum);
        var status = questionnaire.getStatus();
        if (QuestionnaireStatus.PUBLIC.getCode().equals(status)) {
            return qopQuestionnaireVo;
        } else if (QuestionnaireStatus.DELETED.getCode().equals(status) || QuestionnaireStatus.DRAFT.getCode().equals(status)) {
            if (questionnaire.getUid().equals(userId)) {
                return qopQuestionnaireVo;
            }
            return null;
        }
        return null;
    }

    @Override
    public QopQuestionnaireVo getGroupQuestionnaire(String qid, Long groupId, Long userId) {
        var qopGroupMember = qopGroupMemberRepository.findQopGroupMemberByGroupIdAndUserId(groupId, userId);
        if (qopGroupMember == null) {
            throw new FormInfoException(ResponseMsg.GROUP_NOT_FOUND);
        }
        var qopGroupQuestionnaire = qopGroupQuestionnaireRepository.findByGroupIdAndQuestionnaireId(groupId, qid);
        if (qopGroupQuestionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        var qopQuestionnaire = qopQuestionnaireRepository.findByIdAndStatusIsNot(qid, QuestionnaireStatus.DELETED.getCode());
        var qopQuestionnaireVo = new QopQuestionnaireVo();
        BeanUtils.copyProperties(qopQuestionnaire, qopQuestionnaireVo);

        return qopQuestionnaireVo;
    }

    @Override
    public Page<QuestionnaireInfoVo> getMyQuestionnaires(Long userId, Pageable pageable) {
        var allByUid = qopQuestionnaireRepository.findAllByUidAndStatusIsNot(userId, QuestionnaireStatus.DELETED.getCode(), pageable);
        if (allByUid.getTotalElements() > 0) {
            return new PageImpl<>(ListUtils.copyListProperties(allByUid.getContent(), QuestionnaireInfoVo::new), pageable, allByUid.getTotalElements());
        }
        return new PageImpl<>(new ArrayList<>(), pageable, allByUid.getTotalElements());
    }

    @Override
    public List<QuestionnaireInfoVo> getQuestionnaireByGroupId(Long groupId, Long userId) {
        var qopGroupMember = qopGroupMemberRepository.findQopGroupMemberByGroupIdAndUserId(groupId, userId);
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
    public void addQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, Long userId) {
        var questionnaire = new QopQuestionnaire();
        BeanUtils.copyProperties(qopQuestionnaireVo, questionnaire);
        questionnaire.setUid(userId);
        questionnaire.setQuestionNum(qopQuestionnaireVo.getQuestions().size());
        questionnaire.setCreateTime(new Date());
        qopQuestionnaireRepository.insert(questionnaire);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishQuestionnaire(PublishQuestionnaireVo publishQuestionnaireVo, QopQuestionnaire qopQuestionnaire) {
        var now = new Date();
        if (publishQuestionnaireVo.getGroupId() != null) {
            var qopGroupQuestionnaires = new QopGroupQuestionnaire();
            qopGroupQuestionnaires.setQuestionnaireId(publishQuestionnaireVo.getQid());
            qopGroupQuestionnaires.setPublishDate(now);
            qopGroupQuestionnaires.setGroupId(publishQuestionnaireVo.getGroupId());
            qopGroupQuestionnaireRepository.save(qopGroupQuestionnaires);
        }
        if (Boolean.TRUE.equals(publishQuestionnaireVo.getOpen()) && !QuestionnaireStatus.PUBLIC.getCode().equals(qopQuestionnaire.getStatus())) {
            qopQuestionnaire.setStatus(QuestionnaireStatus.PUBLIC.getCode());
            qopQuestionnaire.setPublishTime(now);
            qopQuestionnaireRepository.save(qopQuestionnaire);
        }
        if (Boolean.FALSE.equals(publishQuestionnaireVo.getOpen()) && QuestionnaireStatus.PUBLIC.getCode().equals(qopQuestionnaire.getStatus())) {
            qopQuestionnaire.setStatus(QuestionnaireStatus.DRAFT.getCode());
            qopQuestionnaire.setPublishTime(null);
            qopQuestionnaireRepository.save(qopQuestionnaire);
        }
    }

    @Override
    public void deleteQuestionnaire(QopQuestionnaire qopQuestionnaire) {
        qopQuestionnaire.setStatus(QuestionnaireStatus.DELETED.getCode());
        qopQuestionnaire.setPublishTime(null);
        qopQuestionnaire.setDeleteTime(new Date());
        qopQuestionnaireRepository.save(qopQuestionnaire);
    }

    @Override
    public void updateQuestionnaire(QopQuestionnaireVo qopQuestionnaireVo, QopQuestionnaire qopQuestionnaire) {
        qopQuestionnaire.setTitle(qopQuestionnaireVo.getTitle());
        qopQuestionnaire.setDescription(qopQuestionnaire.getDescription());
        qopQuestionnaire.setQuestionNum(qopQuestionnaireVo.getQuestions().size());
        qopQuestionnaire.setQuestions(qopQuestionnaireVo.getQuestions());
        qopQuestionnaire.setScoringMode(qopQuestionnaireVo.getScoringMode());
        qopQuestionnaireRepository.save(qopQuestionnaire);
    }

    @Override
    public QopQuestionnaire checkQuestionnaireOwner(String qid, Long userId) {
        var questionnaire = qopQuestionnaireRepository.findByIdAndUid(qid, userId);
        if (questionnaire == null) {
            throw new SourceNotFoundException(ResponseMsg.QUESTIONNAIRE_NOT_FOUND);
        }
        return questionnaire;
    }
}
