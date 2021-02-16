package cn.edu.zucc.exception;

/**
 * @ClassName: WrongPasswordException
 * @Description: 密码错误异常
 * @author: Jeff
 * @date: 2021/2/16  20:32
 */
public class WrongPasswordException extends BaseException {
    public WrongPasswordException(String msg, String code) {
        super(msg, code);
    }
}
