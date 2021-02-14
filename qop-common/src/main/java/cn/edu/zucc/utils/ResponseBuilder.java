package cn.edu.zucc.utils;

import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-12-2021
 * <p>
 * 响应数据封装成统一格式
 */
public final class ResponseBuilder {
    private ResponseBuilder() {
    }

    public static <T> ResultVo<T> buildResponse(String code, String msg, T data) {
        return ResultVo.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResultVo<T> buildSuccessResponse() {
        return ResultVo.<T>builder()
                .code(Integer.toString(HttpStatus.OK.value()))
                .msg(ResponseMsg.SUCCESS)
                .build();
    }

    public static <T> ResultVo<T> buildSuccessResponse(T data) {
        return ResultVo.<T>builder()
                .code(Integer.toString(HttpStatus.OK.value()))
                .msg(ResponseMsg.SUCCESS)
                .data(data)
                .build();
    }

    /**
     * <strong>异常必须手动定义</strong>
     *
     * @param e   异常
     * @param <T> -
     * @return -
     */
    public static <T> ResultVo<T> buildErrorResponse(BaseException e) {
        return ResultVo.<T>builder()
                .code(e.getCode())
                .msg(e.getMessage())
                .build();
    }
}
