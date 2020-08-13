package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.constant.COMPONENT;
import com.luna.compile.struct.Context;

public abstract class Component {

    Context context;

    public Component run(Context context, Config config) {
        this.context = context;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public abstract COMPONENT type();

}
