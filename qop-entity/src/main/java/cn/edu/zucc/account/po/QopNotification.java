package cn.edu.zucc.account.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author Bruce
 * @since 04-27-2021
 **/
@Data
@Document(collection = "qop_notification")
public class QopNotification {
    @Id
    private String id;
    @Field("type")
    private Integer type;
    @Field("delete_time")
    private Date deleteTime;
    @Field("uid")
    private Long uid;
    @Field("info")
    private Object info;
}
