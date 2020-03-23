package com.luna.compile.utils;

import java.io.File;

public class SecurityManager {

    private SecurityManager() {}

    private static final SecurityManager instance = new SecurityManager();

    public static SecurityManager getSecurityManager() {
        return instance;
    }



}
