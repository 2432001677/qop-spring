package cn.edu.zucc.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bruce
 * @since 02-13-2021
 * <p>
 * 数据加密
 */
public final class CryptUtils {
    private CryptUtils() {
    }

    /**
     * 用户密码加密
     *
     * @param passwd 用户密码
     * @return 密文
     */
    public static String cryptAccountPasswd(String passwd) {
        return passwd;
    }

    /**
     * 账户密文比较
     *
     * @param rawPasswd 原密码
     * @param passwd    输入的密码
     * @return -
     */
    public static boolean matchAccountPasswd(String rawPasswd, String passwd) {
        return StringUtils.equals(rawPasswd, cryptAccountPasswd(passwd));
    }
}
