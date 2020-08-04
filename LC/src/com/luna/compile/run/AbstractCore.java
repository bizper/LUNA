package com.luna.compile.run;

import com.luna.base.config.Config;

public interface AbstractCore {
    /**
     * init a core with config which comes from command line or other ways.
     * @param config config package
     * @see com.luna.base.config.Config
     * @return itself
     */
    AbstractCore launch(Config config);

    void run(Config config);

    void close();

}
