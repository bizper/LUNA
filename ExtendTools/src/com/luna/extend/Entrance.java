package com.luna.extend;

import com.luna.base.kits.CommandKit;

public class Entrance {

    public static void main(String[] args) {
        System.out.println(CommandKit.get(args).getMessage());
    }

}
