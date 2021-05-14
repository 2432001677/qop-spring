package cn.edu.zucc.analysis.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 05-12-2021
 **/
@Data
public class AnalysisResult {
    private String questionnaireId;
    private Double totalScore;
    private String title;
    private Boolean scoringMode;
    private List<AnalysisQuestion> analysisFormList;
}
