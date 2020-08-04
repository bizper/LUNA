package com.luna.base.kits;

import com.luna.base.result.Bean;
import com.luna.base.config.Config;

public class CommandKit extends BaseKit {

    public static Bean<Config> get(String[] args) {
        Config config = new Config();
        for(int i = 0; i<args.length; i++) {
            String s = args[i];
            if(s.startsWith("-")) {
                s = s.substring(1);
                switch(s) {
                    case "debug":
                        config.setDebug(true);
                    case "O0":
                        config.setCompileMode(0);
                        break;
                    case "O1":
                        config.setCompileMode(1);
                        break;
                    case "s":
                        config.setGenerateBytecodeFile(false);
                        break;
                    case "dp":
                        config.setGeneratePreprocessFile(false);
                        break;
                    case "l":
                        if(i == args.length - 1) return build(false, getFurtherInfo(args, i, "the argument -l without any path."));
                        config.addExtendLibrary(args[++i]);
                        break;
                }
            } else {
                config.addCompileFiles(s);
            }
        }
        return build(true, StringKit.join(args, " "), config);
    }

    private static String getFurtherInfo(String[] a, int loc, String message) {
        int iMax = a.length - 1;
        StringBuilder b = new StringBuilder();
        StringBuilder upper = new StringBuilder();
        for(int i = 0; ; i++) {
            b.append(a[i]);
            for(int j=0; j<a[i].length(); j++) {
                if(i == loc) upper.append('^');
                else upper.append(' ');
            }
            if(i == iMax) {
                b.append("\n");
                break;
            }
            else {
                b.append(' ');
                upper.append(' ');
            }
        }
        return message + "\n" + b.toString() + upper.toString();
    }

}
