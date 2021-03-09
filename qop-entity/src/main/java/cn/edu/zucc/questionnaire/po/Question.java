package cn.edu.zucc.questionnaire.po;

import lombok.Data;

import java.util.Map;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Data
public class Question {
    private String qtype;
    private Boolean require;
    private String qtitle;
    private Map<String, String> options;
}
