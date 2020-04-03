package com.luna.compile.utils;

import java.util.HashMap;
import java.util.Map;

public final class Env {

    private static final Map<String, String> map = new HashMap<>();

    public static void put(String key, String value) {
        map.put(key, value);
    }

    public static String get(String key) {
        return map.get(key);
    }

    public static String getOS() {
        return System.getenv("OS");
    }

    public static String getComputerName() {
        return System.getenv("COMPUTERNAME");
    }

    public static String getUserName() {
        return System.getenv("USERNAME");
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static void clear() {
        map.clear();
    }

}
