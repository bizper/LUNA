package com.luna.extend;

import com.luna.extend.tools.CommandTool;

public class Entrance {

    public static void main(String[] args) {
        System.out.println(CommandTool.get(args).getMessage());
    }

}
