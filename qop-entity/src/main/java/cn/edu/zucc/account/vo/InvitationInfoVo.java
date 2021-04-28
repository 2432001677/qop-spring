package cn.edu.zucc.account.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bruce
 * @since 04-27-2021
 **/
@Data
@Builder
public class InvitationInfoVo {
    private Long inviterId;
    private String inviterName;
    private Long groupId;
    private String groupName;
}
