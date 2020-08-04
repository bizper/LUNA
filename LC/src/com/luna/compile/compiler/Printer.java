package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.struct.Context;

public class Printer extends Component {

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new Printer();
        return instance;
    }

    private Printer() {}

    @Override
    public Component run(Context context, Config config) {
        return this;
    }
}
