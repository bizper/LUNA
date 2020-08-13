package com.luna.base.manager;

import com.luna.compile.compiler.Component;
import com.luna.compile.compiler.Preprocessor;
import com.luna.compile.compiler.TokenStreamChecker;
import com.luna.compile.compiler.Tokenizer;

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
                    break;
                case PRINTER:
                    break;
                case PREPROCESSOR:
                    if(id.get() <= 2) {
                        id.set(3);
                        continue;
                    } else {
                        errMsg = "Preprocessor must follow the Tokenizer or the TokenStreamChecker.";
                        return false;
                    }
                case CODE_GENERATOR:
                    break;
                case SYNTAX_PROCESSOR:
                    break;
                case TOKEN_STREAM_CHECKER:
                    if(id.get() == 1) {
                        id.set(2);
                        continue;
                    } else {
                        errMsg = "TokenStreamChecker must be the second component.";
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
