package cn.edu.zucc.questionnaire.po;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

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
    @Field("option_num")
    private Integer optionNum;
    private Double score;
    @Field("sum_score")
    private Double sumScore;
    private Integer pass;
    private List<Object> options;
}
