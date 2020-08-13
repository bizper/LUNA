package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.constant.COMPONENT;
import com.luna.compile.struct.Context;

public class Linker extends Component {

    private Linker() {}

    private static Component instance;

    @Override
    public COMPONENT type() {
        return COMPONENT.LINKER;
    }

    public static Component getInstance() {
        if(instance == null) instance = new Linker();
        return instance;
    }

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        return this;
    }
}
