package com.example.securitybase.service.excel;

import com.example.securitybase.service.excel.model.ExcelExportOutput;
import com.example.securitybase.service.excel.model.ExcelInfoModel;
import com.example.securitybase.service.excel.model.ExportExcelToPathModel;
import org.springframework.stereotype.Service;

@Service
public class ExcelService implements IExcelService {

    @Override
    public byte[] exportExcelToByte(ExcelInfoModel model) {

        return ExcelUtils.exportToByte(model.getFileTempName(), model.getBeans());
    }

    @Override
    public ExcelExportOutput exportExcelToPath(ExportExcelToPathModel model) {

        return ExcelUtils.exportToPath(model);
    }
}