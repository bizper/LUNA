package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.struct.Context;

public abstract class Component {

    Context context;

    public abstract Component run(Context context, Config config);

    public Context getContext() {
        return context;
    }



}
