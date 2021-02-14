package cn.edu.zucc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupRole {
    GROUP_OWNER("01", "群主"),
    GROUP_ADMIN("02", "管理员"),
    GROUP_MEMBER("03", "组成员");

    private final String code;
    private final String name;
}
