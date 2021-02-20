package com.luna.base.result;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileInfo {

    public FileInfo(File f) {
        try {
            this.path = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String name;

    private BufferedReader path;

    private int line;

    @Override
    public String toString() {
        return "FileInfo{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", line=" + line +
                '}';
    }

    public FileInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        try {
            line ++;
            return path.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
