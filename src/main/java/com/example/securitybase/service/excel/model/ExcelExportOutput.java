package com.example.securitybase.service.excel.model;

import lombok.Data;

@Data
public class ExcelExportOutput {

    private boolean status;
    private String path;
    private String message;
    private Exception ex;
}