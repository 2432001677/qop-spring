package cn.edu.zucc.account.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Bruce
 * @since 04-27-2021
 **/
@Data
public class NotificationVo {
    private String id;
    private Long uid;
    private Integer type;
    private Date createTime;
    private Object info;
}
