package cn.edu.zucc.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bruce
 * @since 02-13-2021
 */
public final class CryptUtils {
    private CryptUtils() {
    }

    public static String cryptAccountPasswd(String passwd) {
        return passwd;
    }

    public static boolean matchAccountPasswd(String rawPasswd, String passwd) {
        return StringUtils.equals(rawPasswd, passwd);
    }
}
