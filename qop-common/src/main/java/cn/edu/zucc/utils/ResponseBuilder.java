package cn.edu.zucc.utils;

import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.common.vo.ResultVo;
import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-12-2021
 */
public final class ResponseBuilder {
    private ResponseBuilder() {
    }

    public static <T> ResultVo<T> buildResponse(Integer code, String msg, T data) {
        return ResultVo.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> buildSuccessResponse(T data) {
        return ResultVo.<T>builder()
                .code(HttpStatus.OK.value())
                .msg(ResponseMsg.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> buildErrorResponse(Integer code, Exception e) {
        return ResultVo.<T>builder()
                .code(code)
                .msg(e.getMessage())
                .build();
    }
}
