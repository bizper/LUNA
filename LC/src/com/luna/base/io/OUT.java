package com.luna.base.io;

import com.luna.base.result.Bean;

public class OUT {

    private static boolean debug = false;

    public static void info(Object obj) {
        System.out.println(obj);
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void trackErr(Object obj) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        System.out.println("\033[31m"+String.format("%-80s %s", stack[2].toString(), obj)+"\033[0m");
    }

    public static void err(Object obj) {
        System.out.println("\033[31m"+obj+"\033[0m");
    }

    public static void openDebug() {
        OUT.debug = true;
    }

    public static void closeDebug() {
        OUT.debug = false;
    }

    public static void debug(Object obj) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if(debug) info(String.format("%-80s %s", stack[2].toString(), obj));
    }

    public static void output(Bean bean) {
        if(bean == null) return;
        if(bean.isSuccess()) {
            info(bean);
        } else {
            trackErr(bean);
        }
    }

    public static void outputMsg(Bean bean) {
        if(bean == null) return;
        if(bean.isSuccess()) {
            info(bean.getMessage());
        } else {
            trackErr(bean.getMessage());
        }
    }

}