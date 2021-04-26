package cn.edu.zucc.questionnaire.po;

import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Data
public class Question {
    private Integer qtype;
    private String qtitle;
    private Boolean required;
    private Integer optionNum;
    private Integer score;
    private Integer pass;
    private List<Object> options;
}
