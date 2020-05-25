package com.luna.base.kits;

import com.luna.compile.struct.StringElement;

import java.util.List;

public class StringKit {

    public static String join(Object[] strings, String separator) {
        if(strings == null) return "";
        StringBuilder sb = new StringBuilder();
        for(Object s : strings) {
            sb.append(s).append(separator);
        }
        if(separator.length() >= 1) sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static <T extends StringElement> String join(List<T> list, String separator) {
        if(list == null || list.size() == 0) return "";
        if(separator == null) separator = "";
        StringBuilder sb = new StringBuilder();
        for(T s : list) {
            sb.append(s.getElement()).append(separator);
        }
        if(separator.length() >= 1) sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String join(Object[] args) {
        return join(args, "");
    }

    public static <T extends StringElement> String join(List<T> args) {
        return join(args, "");
    }

    public static boolean equals(String a, String b) {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;
        return a.equals(b);
    }

}
