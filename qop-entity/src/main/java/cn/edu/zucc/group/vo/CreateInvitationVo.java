package cn.edu.zucc.group.vo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CreateInvitationVo {
    private BigInteger groupId;
    private String userName;
}
