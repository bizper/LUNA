package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.struct.Context;

public class CodeGenerator extends Component {

    private CodeGenerator() {}

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new CodeGenerator();
        return instance;
    }

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        return this;
    }

}
