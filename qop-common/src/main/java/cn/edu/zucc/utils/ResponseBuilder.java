package cn.edu.zucc.utils;

import cn.edu.zucc.common.vo.ResultPageVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.constant.ResponseMsg;
import cn.edu.zucc.exception.BaseException;
import org.springframework.data.domain.Page;
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
        return buildResponse(Integer.toString(HttpStatus.OK.value()), ResponseMsg.SUCCESS, null);
    }

    public static <T> ResultVo<T> buildSuccessResponse(T data) {
        return buildResponse(Integer.toString(HttpStatus.OK.value()), ResponseMsg.SUCCESS, data);
    }

    public static <T> ResultPageVo<T> buildPageableResponse(String code, String msg, Page<T> pageData) {
        return ResultPageVo.<T>builder()
                .code(code)
                .msg(msg)
                .data(pageData.getContent())
                .totalPages(pageData.getTotalPages())
                .size(pageData.getSize())
                .page(pageData.getNumber() + 1)
                .build();
    }

    public static <T> ResultPageVo<T> buildSuccessPageableResponse(Page<T> pageData) {
        return buildPageableResponse(Integer.toString(HttpStatus.OK.value()), ResponseMsg.SUCCESS, pageData);
    }

    /**
     * <strong>异常必须手动定义</strong>
     *
     * @param e   异常
     * @param <T> -
     * @return -
     */
    public static <T> ResultVo<T> buildErrorResponse(BaseException e) {
        return buildResponse(e.getCode(), e.getMessage(), null);
    }
}
