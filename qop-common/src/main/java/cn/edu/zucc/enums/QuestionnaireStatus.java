package cn.edu.zucc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bruce
 * @since 03-15-2021
 */
@AllArgsConstructor
@Getter
public enum QuestionnaireStatus {
    DELETED("已删除", 0),
    DRAFT("草稿", 1),
    GROUP_OPEN("组内公开", 2),
    PUBLIC("完全公开", 3);

    private final String name;
    private final Integer code;
}
