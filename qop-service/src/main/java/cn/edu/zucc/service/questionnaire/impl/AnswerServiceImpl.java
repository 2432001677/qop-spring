package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.questionnaire.po.QopAnswer;
import cn.edu.zucc.questionnaire.vo.QopAnswerVo;
import cn.edu.zucc.repository.questionnaire.QopAnswerRepository;
import cn.edu.zucc.service.questionnaire.AnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Bruce
 * @since 04-11-2021
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private QopAnswerRepository qopAnswerRepository;

    @Override
    public void answerQuestionnaire(QopAnswerVo qopAnswerVo, Long userId) {
        var qopAnswer = new QopAnswer();
        BeanUtils.copyProperties(qopAnswerVo, qopAnswer);
        qopAnswer.setAnswerTime(new Date());
        qopAnswer.setAnswererId(userId);
        qopAnswerRepository.save(qopAnswer);
    }

    @Override
    public void answerGroupQuestionnaire(Long groupId, QopAnswerVo qopAnswerVo, Long userId) {
        var qopAnswer = new QopAnswer();
        BeanUtils.copyProperties(qopAnswerVo, qopAnswer);
        qopAnswer.setAnswerTime(new Date());
        qopAnswer.setAnswererId(userId);
        qopAnswer.setGroupId(groupId);
        qopAnswerRepository.save(qopAnswer);
    }
}
