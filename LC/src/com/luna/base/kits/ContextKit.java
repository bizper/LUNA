package com.luna.base.kits;

import com.luna.base.condition.Condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 上下文关系，对于类似于+=这种符号进行二次构建时使用
 */
public class ContextKit {


    public static <T> List<T> view(List<T> list, int cursor) {
        return view(list, cursor, 3);
    }

    public static <T> List<T> view(T[] t, int cursor) {
        return view(Arrays.asList(t), cursor, 3);
    }
    /**
     * 返回一个数组去观察列表，是列表内容的引用，修改将直接反映到列表之中
     * @param cursor 指定的位置，切面
     * @param list 指定的列表
     * @param length 需要的长度
     * @return 指定位置的上下文
     */
    public static <T> List<T> view(List<T> list, int cursor, int length) {
        List<T> newList = new ArrayList<>(length);
        int target = cursor + length;
        for(; cursor < target; cursor ++) {
            newList.add(cursor >= list.size() ? null : list.get(cursor));
        }
        return newList;
    }

    public static <T> boolean match(List<T> list, int cursor, int length, String regex, Condition<T, String> condition, Condition<List<T>, Boolean> con) {
        List<T> newList = view(list, cursor, length);
        StringBuilder stringBuilder = new StringBuilder();
        if(con.accept(newList)) {
            for(T t : newList) {
                stringBuilder.append(condition.accept(t));
            }
            return stringBuilder.toString().endsWith(regex);
        } else return false;

    }

}