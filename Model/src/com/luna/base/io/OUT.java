package com.luna.base.io;

import com.luna.base.result.Bean;

public class OUT {

    private static boolean debug = false;

    public static void info(Object obj) {
        System.out.println(obj);
    }

    public static void info() {
        System.out.println();
    }

    public static void err(Object obj) {
        System.err.println(obj);
    }

    public static void openDebug() {
        OUT.debug = true;
    }

    public static void closeDebug() {
        OUT.debug = false;
    }

    public static void debug(Object obj) {
        if(debug) info(obj);
    }

    public static void output(Bean bean) {
        if(bean == null) return;
        if(bean.isSuccess()) {
            info(bean);
        } else {
            err(bean);
        }
    }

    public static void outputMsg(Bean bean) {
        if(bean == null) return;
        if(bean.isSuccess()) {
            info(bean.getMessage());
        } else {
            err(bean.getMessage());
        }
    }

}