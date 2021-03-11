package cn.edu.zucc.questionnaire.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * @author Bruce
 * @since 03-11-2021
 */
@Data
@Document(collection = "foo")
public class Foo {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private Map<String,String> options;
}
