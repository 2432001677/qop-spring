package cn.edu.zucc.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author Bruce
 * @since 03-13-2021
 */
public class FormatUtils {
    private FormatUtils() {
    }

    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9_-]{1,20}@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+){1,10}$");
    private static final Pattern PHONE_NUMBER = Pattern.compile("^\\d{5,50}$");

    public static boolean isEmail(String s) {
        return !StringUtils.isBlank(s) && s.length() <= 50 && EMAIL.matcher(s).matches();
    }

    public static boolean isPhoneNumber(String s) {
        return !StringUtils.isBlank(s) && PHONE_NUMBER.matcher(s).matches();
    }
}
