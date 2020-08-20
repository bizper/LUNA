package com.luna.base.manager;

import com.luna.compile.compiler.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SecurityManager {

    private SecurityManager() {}

    private static SecurityManager instance;

    public static SecurityManager getSecurityManager() {
        if(instance == null) instance = new SecurityManager();
        return instance;
    }

    private String errMsg;

    public boolean check(List<Component> path) {
        AtomicInteger id = new AtomicInteger(0);
        for(Component e : path) {
            switch(e.type()) {
                case TOKENIZER:
                    if (id.get() == 0) {
                        id.set(1);
                        continue;
                    } else {
                        errMsg = "Tokenizer must be the first component.";
                        return false;
                    }
                case LINKER:
                    if(id.get() == 4) {
                        id.set(5);
                        continue;
                    } else {
                        errMsg = "Linker must follow the Syntax Processor.";
                        return false;
                    }
                case PRINTER:
                    if(id.get() == 5) {
                        id.set(6);
                        continue;
                    } else {
                        errMsg = "Printer must follow the Linker.";
                        return false;
                    }
                case PREPROCESSOR:
                    if(id.get() <= 2) {
                        id.set(3);
                        continue;
                    } else {
                        errMsg = "Preprocessor must follow the Tokenizer or the TokenStreamChecker.";
                        return false;
                    }
                case CODE_GENERATOR:
                    if(id.get() == 5) {
                        id.set(6);
                        continue;
                    } else {
                        errMsg = "Code Generator must follow the Linker.";
                        return false;
                    }
                case SYNTAX_PROCESSOR:
                    if(id.get() == 3) {
                        id.set(4);
                        continue;
                    } else {
                        errMsg = "Syntax Processor must follow the Preprocessor.";
                        return false;
                    }
                case TOKEN_STREAM_CHECKER:
                    if(id.get() == 1) {
                        id.set(2);
                        continue;
                    } else {
                        errMsg = "Token Stream Checker must be the second component.";
                        return false;
                    }
                default:
                    errMsg = "Non Specified Component added in chain.";
                    return false;
            }
        }
        return true;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
