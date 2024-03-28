package com.example.securitybase.comon.enums;

public enum LogLevel {
    DEBUG(0),
    TRACE(1),
    INFO(2),
    ERROR(3),
    FATAL(4)
    ;
    private final int value;
    LogLevel(int value){
        this.value = value;
    }
    int getValue(){
        return this.value;
    }
}