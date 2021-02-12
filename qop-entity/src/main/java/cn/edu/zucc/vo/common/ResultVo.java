package cn.edu.zucc.vo.common;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bruce
 * @since 02-12-2021
 * <p>
 * 接口响应类
 */
@Data
@Builder
public class ResultVo<T> {
    // 响应码
    private String code;
    // 响应消息
    private String msg;
    // 响应数据
    private T data;
}
