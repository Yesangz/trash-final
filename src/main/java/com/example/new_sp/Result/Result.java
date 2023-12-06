package com.example.new_sp.Result;

import lombok.Data;

@Data
public class Result {
    private Object data;
    private Integer code;
    private String msg;

    public Result(Object data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
