package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.struct.Context;

public class Linker extends Component {

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        return this;
    }
}
