package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.constant.COMPONENT;
import com.luna.compile.struct.Context;

public class Printer extends Component {

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new Printer();
        return instance;
    }

    @Override
    public COMPONENT type() {
        return COMPONENT.PRINTER;
    }

    private Printer() {}

    @Override
    public Component run(Context context, Config config) {
        return this;
    }
}
