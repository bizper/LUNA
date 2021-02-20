package com.luna.base.io.loader;

import com.luna.base.result.FileInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public final class FileReader {

    public static FileInfo read(File file) {
        FileInfo fileInfo = new FileInfo(file);
        fileInfo.setName(file.getName());
        return fileInfo;
    }

}
