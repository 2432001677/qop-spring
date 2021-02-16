package cn.edu.zucc.common.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Bruce
 * @since 02-15-2021
 */
@Data
@Builder
public class ResultPageVo<T> {
    // 响应码
    private String code;
    // 响应消息
    private String msg;
    // 响应数据
    private List<T> data;
    private Integer page;
    private Integer size;
    private Integer totalPages;
}
