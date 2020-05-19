package com.example.helloworld;

public class MyJNI {
    static {
        System.loadLibrary("hello");
    }
    public native static String getString();
}
