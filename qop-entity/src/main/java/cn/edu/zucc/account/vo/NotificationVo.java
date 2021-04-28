package cn.edu.zucc.account.vo;

import lombok.Data;

/**
 * @author Bruce
 * @since 04-27-2021
 **/
@Data
public class NotificationVo {
    private Long uid;
    private Integer type;
    private Object info;
}
