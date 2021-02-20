package com.luna.compile.compiler;

import com.luna.base.config.Config;
import com.luna.compile.constant.COMPONENT;
import com.luna.compile.struct.*;

/**
 * 对于词法进行分析，词法的根基为根目录下的basic.sy文件
 */
public class SyntaxProcessor extends Component {

    private SyntaxProcessor() { }

    @Override
    public COMPONENT type() {
        return COMPONENT.SYNTAX_PROCESSOR;
    }

    private static Component instance;

    public static Component getInstance() {
        if(instance == null) instance = new SyntaxProcessor();
        return instance;
    }

    @Override
    public Component run(Context context, Config config) {
        this.context = context;
        return this;
    }

}