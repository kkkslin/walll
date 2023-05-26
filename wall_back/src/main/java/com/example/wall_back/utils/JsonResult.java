package com.example.wall_back.utils;

public class JsonResult<T> {
    private int code;
    private String error_msg;
    private T data;
    public JsonResult(int code, String msg, T data) {
        this.code = code;
          this.error_msg = msg;
        this.data = data;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}