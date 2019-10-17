package com.luna.compile.loader;

import com.luna.base.io.Out;
import com.luna.base.result.Bean;
import com.luna.compile.constant.CONSTANT;

import java.io.File;

import static com.luna.compile.constant.STATUS.*;

public class FileLoader extends Loader {

    @Override
    public Bean<File> load(String path, Filter<String, Boolean> filter) {
        if(!filter.check(path)) {
            return build(FILE_NOT_VALID, "文件不合法");
        }
        File fin = new File(path);
        if(fin.exists()) {
            return build(OK, "", fin);
        } else {
            for(String prefix : CONSTANT.path) {
                Out.info("trying: " + prefix + path);
                fin = new File(prefix + path);
                if(fin.exists()) return build(OK, "", fin);
            }
        }
        return build(FILE_NOT_FOUND, "找不到文件：" + path);
    }

}
