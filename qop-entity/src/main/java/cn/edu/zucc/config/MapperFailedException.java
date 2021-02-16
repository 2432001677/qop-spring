package cn.edu.zucc.config;

/**
 * @author Bruce
 * @since 02-16-2021
 * <p>
 * 字段映射失败
 */
public class MapperFailedException extends Exception {
    MapperFailedException(String msg) {
        super("字段" + msg + "映射失败");
    }
}
