package com.luna.compile;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.base.kits.CommandKit;
import com.luna.base.result.Bean;
import com.luna.compile.compiler.*;
import com.luna.compile.constant.STATUS;
import com.luna.compile.struct.Context;

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

    private static class Animation extends Thread {

        private final char[] symbols = new char[]{'\\', '|', '/', '-'};

        private int index = 0;

        private boolean flag = true;

        private char get() {
            if(index == symbols.length) index = 0;
            return symbols[index++];
        }

        public void dojoin() {
            flag = false;
        }

        @Override
        public void run() {
            while(flag) {
                try {
                    System.out.print("\rcompiling... " + get());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\rcompile finished.");
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
            path.clear();
            path.add(new Tokenizer());
            path.add(new TokenStreamChecker());
            path.add(new Preprocessor());
            path.add(new SyntaxProcessor());
            path.add(new Linker());
            if(config.isGenerateBytecodeFile()) {
                path.add(new CodeGenerator());
            } else {
                path.add(new Printer());
            }
        }

        private void run(Config config) {
            //Animation animation = new Animation();
            //animation.start();
            init(config);
            Context context = Context.get();
            for(Component component : path) {
                context = component.run(context, config).getContext();
            }
            //animation.dojoin();
            if(context.getCode() != STATUS.OK) {
                for(String err : context.getErrMsg()) {
                    OUT.err(err);
                }
            }
            OUT.info(context);

        }

    }


}
