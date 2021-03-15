package cn.edu.zucc.group.vo;

import cn.edu.zucc.config.JpaEntity;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author Bruce
 * @since 02-14-2021
 */
@JpaEntity
@Data
public class GroupInfoVo {
    private BigInteger id;
    private String groupName;
    private String introduction;
    private String img;
}
