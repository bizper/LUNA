package com.luna.compile.run;

import com.luna.base.config.Config;

public abstract class AbstractCore {

    protected abstract AbstractCore init(Config config);

    protected abstract void run();

    protected abstract void close();

}
