package com.luna.compile.struct;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private Context() {}

    private int code;

    private String msg;

    private List<String> errMsg = new ArrayList<>();

    public List<String> getErrMsg() {
        return errMsg;
    }

    public void addErrMsg(Token token, String msg) {
        errMsg.add("Error:(" + token.getLine() + "," + token.getCol() + ") at " + token.getFileName() + "\n" + msg);
    }

    public void addErrMsg(Token token, boolean offset, String msg) {
        errMsg.add("Error:(" + token.getLine() + "," + (offset ? token.getCol() + token.getValue().length() : token.getCol()) + ") at " + token.getFileName() + "\n" + msg);
    }

    public void addErrMsg(String msg) {
        errMsg.add("Error:(Unknown Source)\n" + msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Context setCode(int code) {
        this.code = code;
        return this;
    }

    public Context setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String toString() {
        return code + " " + msg;
    }

    private List<List<Token>> list = new ArrayList<>();

    public void add(List<Token> module) {
        this.list.add(module);
    }

    public List<Token> get(int number) {
        return list.get(number);
    }

    public List<List<Token>> getList() {
        return list;
    }

    public static Context get() {
        return new Context();
    }

}
