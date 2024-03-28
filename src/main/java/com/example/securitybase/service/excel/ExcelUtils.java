package com.example.securitybase.service.excel;

import com.example.securitybase.service.excel.model.ExcelExportOutput;
import com.example.securitybase.service.excel.model.ExportExcelToPathModel;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static CellStyle cellStyleFormatNumber = null;

    private ExcelUtils() {
    }

    public static <T> void writeData(T item, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        try {
            var itemClassField = item.getClass().getDeclaredFields();

            for (int i = 1; i < itemClassField.length; i++) {
                var field = itemClassField[i];

                //tim field name and param in file template
                field.setAccessible(true);

                Cell cell = row.createCell(i);
                var value = field.get(item);

                if (value == null) {
                    cell.setCellValue("");
                } else {
                    if (value instanceof Double) {
                        cell.setCellValue(String.valueOf(Double.parseDouble(String.valueOf(value))));
                    } else if (value instanceof Date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        cell.setCellValue(dateFormat.format(value));
                    } else {
                        cell.setCellValue(replaceCharSpecial(String.valueOf(value)));
                    }
                }
            }
        } catch (Exception ignore) {
            // ignore
        }
    }

    private static String replaceCharSpecial(String input) {
        String data = input.trim();
        if (data.startsWith("=") || data.startsWith("+") || data.startsWith("-") || data.startsWith("@") || data.startsWith("\n") || data.startsWith("\t"))
            return URLEncoder.encode(data, StandardCharsets.UTF_8);
        return data;
    }

    public static byte[] exportToByte(String fileTemp, Map<String, Object> beans) {
        try {
            var fileInput = new ClassPathResource("template/" + fileTemp);
//            File file = ResourceUtils.getFile("classpath:template/" + fileTemp);
            try (InputStream inputStream = new BufferedInputStream(fileInput.getInputStream())) {
                XLSTransformer transformer = new XLSTransformer();
                try (Workbook workbook = transformer.transformXLS(inputStream, beans)) {
                    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                        workbook.write(bos);
                        return bos.toByteArray();
                    }
                }
            }
        } catch (Exception ignore) {
            return new byte[0];
        }
    }

    public static ExcelExportOutput exportToPath(ExportExcelToPathModel model) {
        var result = new ExcelExportOutput();
        result.setStatus(false);

        try {

            // TODO EXPORT fileOut

            var fileInput = new ClassPathResource("template/" + model.getFileTemp());
            var fileOut = new File(model.getFilename()).getPath();
            // var fileOut = FileUtils.saveFileToTempFolder("output", model.getFileTemp());
            try (InputStream inputStream = new BufferedInputStream(fileInput.getInputStream())) {
                var transformer = new XLSTransformer();
                try (Workbook workbook = transformer.transformXLS(inputStream, model.getData())) {
                    var os = new FileOutputStream(fileOut);
                    workbook.write(os);
                    os.flush();
                    os.close();
                }
            }
            result.setStatus(true);
            result.setPath(fileOut.replaceFirst("/", ""));
            result.setMessage("Export thành công.");
            return result;
        } catch (Exception ex) {
            logger.error("Lỗi export file excel: " + ex.getMessage(), ex);

            result.setStatus(false);
            result.setPath("");
            result.setMessage(ex.getMessage());
            result.setEx(ex);
            return result;
        }
    }

}