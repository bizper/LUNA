package com.luna.compile.run;

import com.luna.base.config.Config;

public abstract class AbstractCore {
    /**
     * init a core with config which comes from command line or other ways.
     * @param config config package
     * @see com.luna.base.config.Config
     * @return itself
     */
    protected abstract AbstractCore init(Config config);

    protected abstract void run();

    protected abstract void close();

}
