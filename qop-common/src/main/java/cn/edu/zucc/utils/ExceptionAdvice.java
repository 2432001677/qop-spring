package cn.edu.zucc.utils;

import cn.edu.zucc.common.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Bruce
 * @since 02-12-2021
 * <p>
 * 全局未知异常处理
 */
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVo<Object> sendErrorResponse(Exception e) {
        return ResponseBuilder.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
    }
}
