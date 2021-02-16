package cn.edu.zucc.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName: WrongPasswordException
 * @Description: 密码错误异常
 * @author: Jeff
 * @date: 2021/2/16  20:32
 */
public class WrongPasswordException extends BaseException {
    public WrongPasswordException() {
        super("密码错误", Integer.toString(HttpStatus.UNAUTHORIZED.value()));
    }
}
