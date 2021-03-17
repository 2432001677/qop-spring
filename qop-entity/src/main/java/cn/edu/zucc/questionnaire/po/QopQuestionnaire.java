package cn.edu.zucc.questionnaire.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author Bruce
 * @since 03-09-2021
 */
@Data
@Document(collection = "qop_questionnaire")
public class QopQuestionnaire {
    @Id
    private String id;
    @Field("uid")
    private Long uid;
    @Field("status")
    private Integer status;
    @Field("title")
    private String title;
    @Field("description")
    private String description;
    @Field("publish_time")
    private Date publishTime;
    @Field("create_time")
    private Date createTime;
    @Field("delete_time")
    private Date deleteTime;
    @Field("question_num")
    private Integer questionNum;
    @Field("questions")
    private List<Question> questions;
}
