package com.example.securitybase.service.excel;

import com.example.securitybase.service.excel.model.ExcelExportOutput;
import com.example.securitybase.service.excel.model.ExcelInfoModel;
import com.example.securitybase.service.excel.model.ExportExcelToPathModel;

public interface IExcelService {

    byte[] exportExcelToByte(ExcelInfoModel model);

    ExcelExportOutput exportExcelToPath(ExportExcelToPathModel model);
}