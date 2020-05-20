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
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Run {

    public static void main(String[] args) {
        if(args.length == 0) {
            OUT.info("-------------------------------------");
            OUT.info("lc author: orange  version: 0x00.0x01");
            OUT.info(" tool for compiling luna code files. ");
            OUT.info("-------------------------------------");
        } else {
            Bean<Config> bean = CommandKit.get(args);//parse the command line arguments
            if(bean.isSuccess()) {
                Env.init();
                Finalizer.getInstance().init(bean.getData()).run();
            } else {
                OUT.trackErr(bean.getMessage());
            }
        }
    }

    private static class Finalizer {

        private final List<Component> path = new ArrayList<>();

        private Finalizer() {
            OUT.info("TARGET COMPUTER:      " + Env.getComputerName());
            OUT.info("TARGET OS:            " + Env.getOS() + " " + Env.getOSVersion());
            OUT.info("OPERATOR:             " + Env.getUserName());
            OUT.info("COMPILE TIME:         " + new Date());
            OUT.info("COMPILER VERSION:     " + "0x00.0x01");
            OUT.info("RANDOM INFO:          " + UUID.randomUUID().toString().replace("-", "").toUpperCase());
            OUT.info("======================================================");
        }

        private static Finalizer instance;

        private Config config;

        private static Finalizer getInstance() {
            if(instance == null) instance = new Finalizer();
            return instance;
        }

        private Finalizer init(Config config) {
            this.config = config;
            //OUT.openDebug();
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
            return this;
        }

        private void close() {
            path.clear();
        }

        private void run() {
            Context context = Context.get();
            for(Component component : path) {
                context = component.run(context, config).getContext();
                if(context.getCode() != STATUS.OK) {
                    for(String err : context.getErrMsg()) {
                        OUT.err(err);
                    }
                    OUT.err(context);
                    return;
                }
            }
            if(context.getCode() != STATUS.OK) {
                for(String err : context.getErrMsg()) {
                    OUT.err(err);
                }
                OUT.err(context);
                return;
            }
            OUT.info(context);
            close();
        }

    }


}
