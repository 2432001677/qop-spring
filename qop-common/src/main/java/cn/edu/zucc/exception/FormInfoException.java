package cn.edu.zucc.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-14-2021
 * <p>
 * 提交信息异常
 */
public class FormInfoException extends BaseException {
    public FormInfoException(String msg) {
        super(msg, Integer.toString(HttpStatus.BAD_REQUEST.value()));
    }
}
