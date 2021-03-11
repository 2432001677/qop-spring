package cn.edu.zucc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestionType {
    SINGLE_CHOICE(0,"单选"),
    MULTIPLE_CHOICE(1,"多选"),
    BLANK(2,"填空"),
    TRUE_OR_FALSE(3,"判断"),
    CASCADE(4,"级联");

    private final int code;
    private final String name;
}
