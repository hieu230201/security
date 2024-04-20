package com.example.securitybase.comon.enums;

public enum SysUserRole {
    AMC_GDPD("G2_AMC_GĐPD"),
    AMC_CVDG("G2_AMC_CVĐG"),
    MB_CVTD("G6_MBTD_CV"),
    MB_GDPD ("G6_MBTD_PD"),
    MB_CHUYEN_VIEN_QLTSDB("G3_MBQLHT_QLTSBD"),
    MB_KIEM_SOAT_QLTSDB("G3_MBQLHT_KS"),
    MB_ADMIN_QLTSDB("G3_MBQLHT_CV"),
    MB_CHUYEN_VIEN_PTDL("G3_MBQLHT_DL");

    private final String value;

    SysUserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
