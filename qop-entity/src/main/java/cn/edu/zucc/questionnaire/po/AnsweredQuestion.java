package cn.edu.zucc.questionnaire.po;

import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 04-10-2021
 */
@Data
public class AnsweredQuestion {
    private Integer qtype;
    private String qtitle;
    private Boolean required;
    private Integer optionNum;
    private Integer score;
    private Integer pass;
    private List<Object> options;
    private List<Object> answer;
    private String content;
}
