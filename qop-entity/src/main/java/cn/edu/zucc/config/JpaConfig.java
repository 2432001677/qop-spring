package cn.edu.zucc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bruce
 * @see JpaEntity
 * @since 02-16-2021
 * <p>
 * 实现非po类支持jpa查询
 */
@Slf4j
@Configuration
public class JpaConfig {
    private static final Pattern humpPattern = Pattern.compile("[A-Z]");

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 初始化注入@JpaEntity对应的Converter
     */
    @PostConstruct
    public void init() {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(JpaEntity.class);
        for (Object o : map.values()) {
            Class clazz = o.getClass();
            GenericConversionService genericConversionService = ((GenericConversionService) DefaultConversionService.getSharedInstance());
            genericConversionService.addConverter(Map.class, clazz, m -> {
                try {
                    return copyMapToObj(m, clazz.getDeclaredConstructor().newInstance());
                } catch (MapperFailedException e) {
                    log.error(e.getMessage(), e);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    /**
     * 将map中的值copy到entity中对应的字段上
     *
     * @param map    -
     * @param target -
     * @return -
     */
    private static Object copyMapToObj(Map<String, Object> map, Object target) throws MapperFailedException {
        if (map == null || target == null || map.isEmpty()) {
            return target;
        }
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            String key = lowerCamelToLine(targetPd.getName());
            Object value = map.get(key);
            if (!map.containsKey(key)) {
                continue;
            }
            Method writeMethod = targetPd.getWriteMethod();
            try {
                writeMethod.invoke(target, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MapperFailedException(key);
            }
        }
        return target;
    }

    /**
     * 小驼峰转下划线
     *
     * @param str -
     * @return -
     */
    private static String lowerCamelToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}