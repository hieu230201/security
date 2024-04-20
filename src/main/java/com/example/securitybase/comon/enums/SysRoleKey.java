package com.example.securitybase.comon.enums;

public enum SysRoleKey {
    GD_CHI_NHANH("G6_MBTD_PD,G1_MBCN_PD"),
    PGD_CHI_NHANH("PGD_CHI_NHANH");

    private final String value;

    SysRoleKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
