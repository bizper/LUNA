package com.luna.base.result;

import java.util.ArrayList;
import java.util.List;

public class FileInfo {

    public FileInfo() {
        this.content = new ArrayList<>();
    }

    private String name;

    private String path;

    private List<String> content;

    @Override
    public String toString() {
        return "FileInfo{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", content=" + content +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public List<String> getContent() {
        return content;
    }
}
