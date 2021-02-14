package cn.edu.zucc.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-14-2021
 * <p>
 * 操作未经授权
 */
public class UnAuthorizedException extends BaseException {
    public UnAuthorizedException(String msg) {
        super(msg, Integer.toString(HttpStatus.UNAUTHORIZED.value()));
    }
}
