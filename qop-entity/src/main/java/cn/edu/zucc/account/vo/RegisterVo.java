package cn.edu.zucc.account.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterVo {

    private Long id;

    private String nickName;

    private String phoneNumber;

    private String email;

    private String password;

    private String img;

    private String accountStatus;

    private Date createDate;
}
