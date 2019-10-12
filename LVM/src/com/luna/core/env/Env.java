package com.luna.core.env;

import com.luna.core.element.Element;

import java.util.HashMap;

public class Env {

    private HashMap<String, Element> env = new HashMap<>();

    public void put(String s, Element ele) {
        env.put(s, ele);
    }

    public Element get(String s) {
        return env.get(s);
    }

}
