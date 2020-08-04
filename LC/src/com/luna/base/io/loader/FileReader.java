package com.luna.base.io.loader;

import com.luna.base.result.FileInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public final class FileReader {

    public static FileInfo read(File file) {
        FileInfo fileInfo = new FileInfo();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            fileInfo.setName(file.getName());
            fileInfo.setPath(file.getAbsolutePath());
            String str;
            while((str = br.readLine()) != null) {
                fileInfo.getContent().add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    public static FileInfo read(File file, boolean openConnectionSymbol, String connectionSymbol) {
        FileInfo fileInfo = new FileInfo();
        List<String> content = fileInfo.getContent();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            fileInfo.setName(file.getName());
            fileInfo.setPath(file.getAbsolutePath());
            String str;
            StringBuilder key = new StringBuilder();
            while((str = br.readLine()) != null) {
                if(openConnectionSymbol) {
                    if(str.isEmpty()) continue;
                    if(key.length() > 0) {
                        if(str.trim().endsWith(connectionSymbol)) key.append(str.replace(connectionSymbol, ""));
                        else {
                            content.add(key + str.trim());
                            key = new StringBuilder();
                        }
                    } else {
                        if(str.trim().endsWith(connectionSymbol)) key.append(str.replace(connectionSymbol, ""));
                        else content.add(str);
                    }
                } else {
                    if(str.isEmpty()) continue;
                    content.add(str.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

}
