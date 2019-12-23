package com.luna.base.kits;

public class StringKit {

    public static String join(String separator, String[] strings) {
        StringBuilder sb = new StringBuilder();
        for(String s : strings) {
            sb.append(s).append(separator);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String join(String[] args) {
        return join("", args);
    }

    public static boolean equals(String a, String b) {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;
        return a.equals(b);
    }

}
