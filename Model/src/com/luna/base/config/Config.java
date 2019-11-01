package com.luna.base.config;

import java.util.ArrayList;
import java.util.List;

public class Config {

    /**
     * start with -O, use like -O0 -O1
     * 0 normal
     * 1 do something better
     */
    private int compileMode = 0;

    /**
     * files waiting for compiling. not start with any options
     */
    private List<String> compileFiles = new ArrayList<>();

    /**
     * start with -s to disable generation
     */
    private boolean generateBytecodeFile = true;

    /**
     * start with -dp to disable generation
     */
    private boolean generatePreprocessFile = true;

    /**
     * start with -l to add extend library
     */
    private List<String> extendLibrary = new ArrayList<>();

    @Override
    public String toString() {
        return "Config [" +
                "compileMode=" + compileMode + '\n' +
                ", compileFiles=" + compileFiles + '\n' +
                ", generateBytecodeFile=" + generateBytecodeFile + '\n' +
                ", generatePreprocessFile=" + generatePreprocessFile + '\n' +
                ", extendLibrary=" + extendLibrary + '\n' +
                ']';
    }

    public Config setGeneratePreprocessFile(boolean generatePreprocessFile) {
        this.generatePreprocessFile = generatePreprocessFile;
        return this;
    }

    public boolean isGeneratePreprocessFile() {
        return generatePreprocessFile;
    }

    public Config setExtendLibrary(List<String> extendLibrary) {
        this.extendLibrary = extendLibrary;
        return this;
    }

    public Config addExtendLibrary(String extendLibrary) {
        this.extendLibrary.add(extendLibrary);
        return this;
    }

    public Config setCompileFiles(List<String> compileFiles) {
        this.compileFiles = compileFiles;
        return this;
    }

    public List<String> getExtendLibrary() {
        return extendLibrary;
    }

    public Config setCompileMode(int compileMode) {
        this.compileMode = compileMode;
        return this;
    }

    public Config addCompileFiles(String compileFiles) {
        this.compileFiles.add(compileFiles);
        return this;
    }

    public Config setGenerateBytecodeFile(boolean generateBytecodeFile) {
        this.generateBytecodeFile = generateBytecodeFile;
        return this;
    }


    public int getCompileMode() {
        return compileMode;
    }

    public String[] getCompileFiles() {
        return compileFiles.toArray(new String[]{});
    }

    public boolean isGenerateBytecodeFile() {
        return generateBytecodeFile;
    }

}
