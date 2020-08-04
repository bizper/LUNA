package com.luna.compile;

import com.luna.compile.run.AbstractLauncher;
import com.luna.compile.run.Core;

public class Launcher extends AbstractLauncher {

    public static void main(String[] args) {
        launch(Core.class, args);
    }

}
