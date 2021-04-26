package cn.edu.zucc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bruce
 * @since 04-26-2021
 **/
@AllArgsConstructor
@Getter
public enum PassType {
    NONE(0, "无"),
    PLANNING(1, "计划"),
    ATTENTION(2, "注意"),
    SIMULTANEOUS(3, "同时性加工"),
    SUCCESSIVE(4, "继时性加工");

    private final Integer code;
    private final String name;
}