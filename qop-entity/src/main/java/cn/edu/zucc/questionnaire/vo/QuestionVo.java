package cn.edu.zucc.questionnaire.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 03-11-2021
 */
@Data
public class QuestionVo {
    private String qtitle;
    private String qtype;
    private Boolean require;
    private List<String> options;
}
