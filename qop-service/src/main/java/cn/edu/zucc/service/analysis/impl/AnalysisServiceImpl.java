package cn.edu.zucc.service.analysis.impl;

import cn.edu.zucc.analysis.vo.AnalysisQuestion;
import cn.edu.zucc.analysis.vo.AnalysisResult;
import cn.edu.zucc.questionnaire.po.QopQuestionnaire;
import cn.edu.zucc.service.analysis.AnalysisService;
import cn.edu.zucc.utils.BruceBsonUtils;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
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
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public AnalysisResult getAnalysis(QopQuestionnaire qopQuestionnaire) {
        String qid = qopQuestionnaire.getId();
        var analysisResult = new AnalysisResult();
        BeanUtils.copyProperties(qopQuestionnaire, analysisResult);
        analysisResult.setQuestionnaireId(qid);
        analysisResult.setAnalysisFormList(analysisAnswers(qopQuestionnaire));
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
        AggregateIterable<Document> avgOutput = mongoTemplate.getCollection("qop_answer").aggregate(BruceBsonUtils.getBson(qopQuestionnaire.getId()));
        avgOutput.forEach(output -> analysisQuestionList.get(output.getInteger("_id")).setAverageScore(output.getDouble("average_score")));
        return analysisQuestionList;
    }
}
