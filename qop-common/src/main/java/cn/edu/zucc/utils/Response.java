package cn.edu.zucc.utils;

import cn.edu.zucc.constant.ResponseCode;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.vo.common.ResultVo;

/**
 * @author Bruce
 * @since 02-12-2021
 */
public class Response {
    public static <T> ResultVo<T> getResponse(String code, String msg, T data) {
        return ResultVo.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> getSuccessResponse(T data) {
        return ResultVo.<T>builder()
                .code(ResponseCode.OK)
                .msg(ResponseMsg.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> getErrorResponse(Exception e) {
        return ResultVo.<T>builder()
                .code(ResponseCode.BAD)
                .msg(e.getMessage())
                .build();
    }
}
