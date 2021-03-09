package cn.edu.zucc.service.questionnaire.impl;

import cn.edu.zucc.repository.questionnaire.QopQuestionnaireRepository;
import cn.edu.zucc.service.questionnaire.QuestionnaireService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Resource
    private QopQuestionnaireRepository qopQuestionnaireRepository;
}
