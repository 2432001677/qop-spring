package cn.edu.zucc.questionnaire.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Bruce
 * @since 03-14-2021
 */
@Data
public class QuestionnaireInfoVo {
    private String id;
    private Integer status;
    private String title;
    private String description;
    private Boolean scoringMode;
    private Date createTime;
    private Date publishTime;
    private Integer questionNum;
}
