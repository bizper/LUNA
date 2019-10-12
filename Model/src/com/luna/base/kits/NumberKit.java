package com.luna.base.kits;

public class NumberKit {

    public static Integer toInt(Object obj, Integer defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Integer toInt(Object obj) {
        return toInt(obj, 0);
    }

}
