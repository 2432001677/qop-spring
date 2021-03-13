package cn.edu.zucc.account.vo;

import lombok.Data;

/**
 * @author Bruce
 * @since 03-13-2021
 */
@Data
public class ChangePasswordVo {
    private String userName;
    private String validPassword;
    private String password;
}
