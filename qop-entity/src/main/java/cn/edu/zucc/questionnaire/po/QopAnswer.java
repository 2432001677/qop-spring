package cn.edu.zucc.questionnaire.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author Bruce
 * @since 04-10-2021
 */
@Data
@Document(collection = "qop_answer")
public class QopAnswer {
    @Id
    private String id;
    @Field("questionnaire_id")
    private String questionnaireId;
    @Field("answerer_id")
    private Long answererId;
    @Field("total_score")
    private Double totalScore;
    @Field("title")
    private String title;
    @Field("description")
    private String description;
    @Field("answer_time")
    private Date answerTime;
    @Field("delete_time")
    private Date deleteTime;
    @Field("question_num")
    private Integer questionNum;
    @Field("answer_num")
    private Integer answerNum;
    @Field("answered_questions")
    private List<AnsweredQuestion> answeredQuestions;
}
