package cn.edu.zucc.service.analysis;

import cn.edu.zucc.analysis.vo.AnalysisResult;
import cn.edu.zucc.questionnaire.po.QopQuestionnaire;

/**
 * @author Bruce
 * @since 05-12-2021
 **/
public interface AnalysisService {
    AnalysisResult getAnalysis(QopQuestionnaire qopQuestionnaire);
}
