package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.struct.Context;
import com.luna.compile.struct.Module;
import com.luna.compile.utils.syntax.SyntaxParser;

/**
 * 对于语法进行分析，语法的根基为根目录下的basic.sy文件
 */
public class SyntaxProcessor extends Component {

    private SyntaxProcessor() {
        SyntaxParser.init();
    }

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new SyntaxProcessor();
        return instance;
    }

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        for(final Module module : context.getModules()) {

        }
        return this;
    }

}
