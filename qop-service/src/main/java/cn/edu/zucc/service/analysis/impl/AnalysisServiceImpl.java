package cn.edu.zucc.service.analysis.impl;

import cn.edu.zucc.analysis.vo.AnalysisQuestion;
import cn.edu.zucc.analysis.vo.AnalysisResult;
import cn.edu.zucc.analysis.vo.SelectOption;
import cn.edu.zucc.enums.QuestionType;
import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.repository.questionnaire.QopAnswerRepository;
import cn.edu.zucc.service.analysis.AnalysisService;
import cn.edu.zucc.utils.BruceBsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruce
 * @since 05-12-2021
 **/
@Service
public class AnalysisServiceImpl implements AnalysisService {
    public static final String QOP_ANSWER = "qop_answer";
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private QopAnswerRepository qopAnswerRepository;

    @Override
    public AnalysisResult getAnalysis(QopQuestionnaire qopQuestionnaire) {
        var qid = qopQuestionnaire.getId();
        var analysisResult = new AnalysisResult();
        BeanUtils.copyProperties(qopQuestionnaire, analysisResult);
        analysisResult.setQuestionnaireId(qid);
        analysisResult.setAnalysisFormList(analysisAnswers(qopQuestionnaire));
        analysisResult.setAnswerCount(qopAnswerRepository.countByQuestionnaireId(qid));
        return analysisResult;
    }

    private List<AnalysisQuestion> analysisAnswers(QopQuestionnaire qopQuestionnaire) {
        int questionNum = qopQuestionnaire.getQuestionNum();
        var analysisQuestionList = new ArrayList<AnalysisQuestion>(questionNum);
        for (var i = 0; i < questionNum; i++) {
            var analysisQuestion = new AnalysisQuestion();
            BeanUtils.copyProperties(qopQuestionnaire.getQuestions().get(i), analysisQuestion);
            analysisQuestionList.add(analysisQuestion);
        }
        var avgOutput = mongoTemplate.getCollection(QOP_ANSWER).aggregate(BruceBsonUtils.getAvgScore(qopQuestionnaire.getId()));
        avgOutput.forEach(output -> analysisQuestionList.get(output.getInteger("_id")).setAverageScore(output.getDouble("average_score")));
        for (var i = 0; i < questionNum; i++) {
            var analysisQuestion = analysisQuestionList.get(i);
            int qtype = analysisQuestion.getQtype();
            var question = qopQuestionnaire.getQuestions().get(i);
            var questionOptions = question.getOptions();
            var options = new ArrayList<SelectOption>(questionOptions.size());
            questionOptions.forEach(opt -> {
                var o = new SelectOption();
                BeanUtils.copyProperties(opt, o);
                o.setSelectedCount(0);
                options.add(o);
            });
            if (QuestionType.SINGLE_SELECT.getCode() == qtype || QuestionType.RATES.getCode() == qtype || QuestionType.DROP_DOWN_SELECT.getCode() == qtype || QuestionType.AUDIO.getCode() == qtype) {
                var optionOutput = mongoTemplate.getCollection(QOP_ANSWER).aggregate(BruceBsonUtils.getSingleOption(qopQuestionnaire.getId(), i, qtype));
                optionOutput.forEach(data -> options.get(data.getInteger("_id")).setSelectedCount(data.getInteger("count")));
            } else if (QuestionType.MULTIPLE_SELECT.getCode() == qtype) {
                var optionOutput = mongoTemplate.getCollection(QOP_ANSWER).aggregate(BruceBsonUtils.getMultiOption(qopQuestionnaire.getId(), i));
                optionOutput.forEach(data -> options.get(data.getInteger("_id")).setSelectedCount(data.getInteger("count")));
            } else if (QuestionType.BLANK.getCode() == qtype) {
                var optionOutput = mongoTemplate.getCollection(QOP_ANSWER).aggregate(BruceBsonUtils.getBlankAnswer(qopQuestionnaire.getId(), i));
                optionOutput.forEach(data -> {
                    var selectOption = new SelectOption();
                    selectOption.setText(data.getString("content"));
                    options.add(selectOption);
                });
            } else if (QuestionType.WEIGHT_ASSIGN.getCode() == qtype) {
                // todo
            }
            analysisQuestion.setOptions(options);
        }
        return analysisQuestionList;
    }
}
