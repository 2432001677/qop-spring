package cn.edu.zucc.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 *@author Bruce
 *@since 03-14-2021
 */
public class ListUtils extends BeanUtils {
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }

    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanUtilsCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
            list.add(t);
        }
        return list;
    }
}
