package com.luna.compile;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.base.kits.CommandKit;
import com.luna.base.result.Bean;
import com.luna.compile.compiler.*;
import com.luna.compile.constant.STATUS;
import com.luna.compile.struct.Context;
import com.luna.compile.utils.Env;

import java.util.ArrayList;
import java.util.List;

public class Run {

    public static void main(String[] args) {
        if(args.length == 0) {
            OUT.info("-------------------------------------");
            OUT.info("lc author: orange  version: 0x00.0x01");
            OUT.info("for compiling luna code files.");
            OUT.info("-------------------------------------");
        } else {
            Bean<Config> bean = CommandKit.get(args);
            if(bean.isSuccess()) {
                Finalizer.getInstance().run(bean.getData());
            } else {
                OUT.err(bean.getMessage());
            }
        }
    }

    private static class Finalizer {

        private final List<Component> path = new ArrayList<>();

        private Finalizer() {}

        private static Finalizer instance;

        private static Finalizer getInstance() {
            if(instance == null) instance = new Finalizer();
            return instance;
        }

        private void init(Config config) {
            OUT.openDebug();
            this.initParams();
            path.clear();
            path.add(Tokenizer.getInstance());
            path.add(TokenStreamChecker.getInstance());
            path.add(Preprocessor.getInstance());
            path.add(SyntaxProcessor.getInstance());
            path.add(Linker.getInstance());
            if(config.isGenerateBytecodeFile()) {
                path.add(CodeGenerator.getInstance());
            } else {
                path.add(Printer.getInstance());
            }
        }

        private void initParams() {
            Env.put("syntax_file_path", "./LC/src/basic.sy");
        }

        private void close() {
            path.clear();
        }

        private void run(Config config) {
            init(config);
            Context context = Context.get();
            for(Component component : path) {
                context = component.run(context, config).getContext();
                if(context.getCode() != STATUS.OK) {
                    for(String err : context.getErrMsg()) {
                        OUT.err(err);
                    }
                    OUT.info(context);
                    return;
                }
            }
            if(context.getCode() != STATUS.OK) {
                for(String err : context.getErrMsg()) {
                    OUT.err(err);
                }
            }
            OUT.info(context);
            close();
        }

    }


}
