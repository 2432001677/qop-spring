package cn.edu.zucc.questionnaire.vo;

import cn.edu.zucc.questionnaire.po.Question;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Bruce
 * @since 03-11-2021
 */
@Data
public class QopQuestionnaireVo {
    private String id;
    private Integer status;
    private String title;
    private Integer answerNum;
    private String description;
    private Boolean scoringMode;
    private Date createTime;
    private Date publishTime;
    private Integer questionNum;
    private List<Question> questions;
}
