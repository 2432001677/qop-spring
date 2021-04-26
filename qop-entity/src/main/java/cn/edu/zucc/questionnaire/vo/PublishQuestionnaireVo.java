package cn.edu.zucc.questionnaire.vo;

import lombok.Data;

/**
 * @author Bruce
 * @since 03-15-2021
 */
@Data
public class PublishQuestionnaireVo {
    private String qid;
    private Boolean open;
    private Long groupId;
}
