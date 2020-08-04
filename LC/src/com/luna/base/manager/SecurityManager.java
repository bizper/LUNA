package com.luna.base.manager;

public class SecurityManager {

    private SecurityManager() {}

    private static SecurityManager instance;

    public static SecurityManager getSecurityManager() {
        if(instance == null) instance = new SecurityManager();
        return instance;
    }

    public void eval(String luna) {

    }

}
