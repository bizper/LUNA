package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.base.result.Bean;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Module;
import com.luna.compile.struct.TokenRepresent;
import com.luna.compile.struct.TokenSequence;
import com.luna.compile.utils.TypeFinalizer;

import java.util.List;

import static com.luna.compile.constant.STATUS.TOKEN_TYPE_ERROR;

public class TokenStreamChecker extends Component {

    private static Component instance;

    private static final String TYPE_CHECK_ERROR = "TYPE CHECK ERROR";

    public static Component getInstance() {
        if(instance == null) instance = new TokenStreamChecker();
        return instance;
    }

    private TokenStreamChecker() {}

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        final List<Module> modules = context.getModules();
        for(Module module : modules) {

        }
        return this;
    }

}
