package com.example.new_sp.Result;

public class Code {
    //正常码
    public static final Integer SAVE_OK = 20011;
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;

    //错误码
    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;

    //异常码
    public static final Integer SYSTEM_UNKNOWN_ERROR = 50001;
    public static final Integer SYSTEM_TIMEOUT_ERROR = 50002;
    public static final Integer SYSTEM_VALIDATE_ERROR = 60001;
    public static final Integer SYSTEM_BUSINESS_ERROR = 60002;
}
