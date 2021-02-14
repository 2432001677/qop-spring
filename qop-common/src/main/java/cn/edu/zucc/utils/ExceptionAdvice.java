package cn.edu.zucc.utils;

import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Bruce
 * @since 02-12-2021
 * <p>
 * 异常处理
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
    /**
     * 全局未知异常处理
     *
     * @param e -
     * @return -
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVo<Void> sendErrorResponse(Throwable e) {
        log.error("unknowing exception", e);
        return ResponseBuilder.buildResponse(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage(), null);
    }

    /**
     * 自定义异常处理
     *
     * @param e -
     * @return -
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo<Void> sendFormInfoErrResponse(BaseException e) {
        return ResponseBuilder.buildErrorResponse(e);
    }
}
