package com.luna.core.element;

import com.luna.core.constant.ESTATUS;

public interface Element {

    //Life Cycle
    void create();

    void destroy();

    //hook
    String name();

    ESTATUS status();

    String info();

}
