package cn.edu.zucc.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

/**
 * @author Bruce
 * @since 02-13-2021
 * <p>
 * 数据加密
 */
public final class CryptUtils {
    @Value("${spring.datasource.salt}")
    private static String salt;

    private CryptUtils() {
    }

    /**
     * 用户密码加密
     *
     * @param passwd 用户密码
     * @return 密文
     */
    public static String cryptAccountPasswd(String passwd) {
        String str = DigestUtils.md5DigestAsHex(passwd.getBytes())+salt;
        System.out.println(str);
        return str;
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
