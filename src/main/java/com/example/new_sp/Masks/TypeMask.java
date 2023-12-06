package com.example.new_sp.Masks;

public class TypeMask {
    public static final int Customer = 0b0000000000000001;
    public static final int Salesperson = 0b0000000000000010;
    public static final int Supplier = 0b0000000000000100;

    public static final int self = 0b0000000000010000;
    public static final int type = 0b0000000000100000;
    public static final int all = 0b0000000001000000;
}
