package cn.edu.zucc.exception;


/**
 * @author Bruce
 * @since 02-12-2021
 * <p>
 * 所有自定义异常需要继承它
 */
public class BaseException extends RuntimeException {
    private final String code;

    public BaseException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
