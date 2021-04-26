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
    PUBLIC("公开", 2);

    private final String name;
    private final Integer code;
}
