package com.example.securitybase.comon.enums;

public enum SysGroupType {
    HOI_SO("1"),
    CHI_NHANH("2"),
    AMC("3");

    private final String value;

    SysGroupType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
