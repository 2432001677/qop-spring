package cn.edu.zucc.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-14-2021
 * <p>
 * 全局未捕获异常
 */
public class UnexpectedException extends BaseException {
    public UnexpectedException(Exception e) {
        super(e.getMessage(), Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
