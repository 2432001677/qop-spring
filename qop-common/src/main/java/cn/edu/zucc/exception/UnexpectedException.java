package cn.edu.zucc.exception;

/**
 * @author Bruce
 * @since 02-12-2021
 */
public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String msg) {
        super(msg);
    }
}
