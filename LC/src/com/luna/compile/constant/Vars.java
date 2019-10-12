package com.luna.compile.constant;

import java.util.HashMap;

public class Vars {

    private static final HashMap<String, String> hashMap = new HashMap<>();

    public static void put(String key, String value) {
        hashMap.put(key, value);
    }

    public static String get(String key) {
        return hashMap.get(key);
    }

}
