package com.luna.compile.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public static String getDateString() {
        return DateUtil.toDateString();
    }

    public static String getRandomUppercaseUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String getVersion() {
        return "0x00.0x01";
    }

    public static void clear() {
        map.clear();
    }

    public static void init() {
        clear();
        put("syntax_file_path", "./LC/src/basic.sy");
        put("test_syntax_file_path", "./LC/src/extend.sy");
    }

}
