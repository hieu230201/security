package com.example.securitybase.service.excel.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ExportExcelToPathModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String filename;
    private String fileTemp;
    private Map<String, Object> data;
}