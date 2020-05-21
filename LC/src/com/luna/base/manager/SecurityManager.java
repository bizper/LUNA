package com.luna.base.manager;

public class SecurityManager {

    private SecurityManager() {}

    private static final SecurityManager instance = new SecurityManager();

    public static SecurityManager getSecurityManager() {
        return instance;
    }



}
