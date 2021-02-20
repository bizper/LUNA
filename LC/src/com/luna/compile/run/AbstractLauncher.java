package com.luna.compile.run;

import com.luna.base.config.Config;
import com.luna.base.io.OUT;
import com.luna.base.kits.CommandKit;
import com.luna.base.result.Bean;
import com.luna.compile.utils.Env;

public class AbstractLauncher {

    public static void launch(Class<? extends AbstractCore> clazz, String[] args) {
        printBanner();
        if(args == null || args.length == 0) return;
        try {
            AbstractCore core = clazz.getDeclaredConstructor().newInstance();
            Bean<Config> bean = CommandKit.get(args);//parse the command line arguments
            if(bean.isSuccess()) {
                Env.init();
                core.launch(bean.getData());
            } else {
                OUT.trackErr(bean.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printBanner() {
        OUT.info("======================================================");
        OUT.info("lc author: orange  version: 0x00.0x01");
        OUT.info(" tools for compiling luna source files. ");
        OUT.info("------------------------------------------------------");
        OUT.info("TARGET COMPUTER:      " + Env.getComputerName());
        OUT.info("TARGET OS:            " + Env.getOS() + " " + Env.getOSVersion());
        OUT.info("OPERATOR:             " + Env.getUserName());
        OUT.info("COMPILE TIME:         " + Env.getDateString());
        OUT.info("COMPILER VERSION:     " + Env.getVersion());
        OUT.info("RANDOM INFO:          " + Env.getRandomUppercaseUUID());
        OUT.info("======================================================");
    }

}
