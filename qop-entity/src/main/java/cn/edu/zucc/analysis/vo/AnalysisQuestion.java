package cn.edu.zucc.analysis.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 05-12-2021
 **/
@Data
public class AnalysisQuestion {
    private String qtitle;
    private Integer qtype;
    private Boolean required;
    private Integer answerCount;
    private Double averageScore;
    private Integer pass;
    private List<Object> options;
}
