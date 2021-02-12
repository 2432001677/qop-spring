package cn.edu.zucc.utils;

import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.vo.common.ResultVo;
import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-12-2021
 */
public class Response {
    public static <T> ResultVo<T> getResponse(Integer code, String msg, T data) {
        return ResultVo.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> getSuccessResponse(T data) {
        return ResultVo.<T>builder()
                .code(HttpStatus.OK.value())
                .msg(ResponseMsg.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> getErrorResponse(Integer code, Exception e) {
        return ResultVo.<T>builder()
                .code(code)
                .msg(e.getMessage())
                .build();
    }
}
