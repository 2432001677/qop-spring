package cn.edu.zucc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestionType {
    SINGLE_SELECT(0, "单选"),
    MULTIPLE_SELECT(1, "多选"),
    BLANK(2, "填空"),
    RATES(3, "评分"),
    CASCADE(4, "级联"),
    DROP_DOWN_SELECT(5, "下拉"),
    WEIGHT_ASSIGN(6, "权重"),
    UPLOAD_FILE(7, "附件"),
    AUDIO(8, "听力");

    private final int code;
    private final String name;
}
