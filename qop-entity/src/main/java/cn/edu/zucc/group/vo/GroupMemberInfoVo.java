package cn.edu.zucc.group.vo;

import cn.edu.zucc.config.JpaEntity;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author Bruce
 * @since 02-15-2021
 */
@JpaEntity
@Data
public class GroupMemberInfoVo {
    private BigInteger userId;
    private String nickName;
    private String userRole;
    private Date joinDate;
}
