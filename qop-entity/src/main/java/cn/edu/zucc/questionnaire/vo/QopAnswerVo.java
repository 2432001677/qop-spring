package cn.edu.zucc.questionnaire.vo;

import cn.edu.zucc.questionnaire.po.AnsweredQuestion;
import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 04-11-2021
 */
@Data
public class QopAnswerVo {
    private String questionnaireId;
    private Long answererId;
    private Double totalScore;
    private String title;
    private String description;
    private Integer questionNum;
    private Integer answerNum;
    private List<AnsweredQuestion> answeredQuestions;
}
