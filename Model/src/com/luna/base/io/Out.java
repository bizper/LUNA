package com.luna.base.io;

import com.luna.base.result.Bean;

public class Out {

    public static void info(Object obj) {
        System.out.println(obj);
    }

    public static void info() {
        System.out.println();
    }

    public static void err(Object obj) {
        System.err.println(obj);
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