package cn.edu.zucc.questionnaire.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author Bruce
 * @since 03-11-2021
 */
@Data
@Document(collection = "trash_questionnaire")
public class QopTrashQuestionnaire {
    @Id
    private String id;
    @Field("uid")
    private Integer uid;
    @Field("status")
    private Integer status;
    @Field("title")
    private String title;
    @Field("description")
    private String description;
    @Field("create_time")
    private Date createTime;
    @Field("delete_time")
    private Date deleteTime;
    @Field("questions")
    private List<Question> questions;
}
