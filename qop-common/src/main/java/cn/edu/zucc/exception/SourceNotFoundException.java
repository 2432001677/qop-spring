package cn.edu.zucc.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Bruce
 * @since 02-14-2021
 * <p>
 * 资源未找到
 */
public class SourceNotFoundException extends BaseException {
    public SourceNotFoundException(String msg) {
        super(msg, Integer.toString(HttpStatus.NOT_FOUND.value()));
    }
}
