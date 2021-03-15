package cn.edu.zucc.utils;

/**
 * @author Bruce
 * @since 03-14-2021
 */
@FunctionalInterface
public interface BeanUtilsCallBack<S,T> {
    void callBack(S s, T t);
}
