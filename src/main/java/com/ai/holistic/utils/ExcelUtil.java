package com.ai.holistic.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {

    //private static final String EXCEL_PATH = "src/main/resources/testdata.xlsx";
    //private static final String EXCEL_PATH = ConfigReader.getProperty("excel.path");

    public static Object[][] getData(String excelPath, String sheetName) {

        Object[][] data = null;

        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            data = new Object[rowCount - 1][colCount];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = getCellValue(cell);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case BOOLEAN: return cell.getBooleanCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell) ?
                        cell.getDateCellValue() :
                        cell.getNumericCellValue();
            case FORMULA: return cell.getCellFormula();
            default:      return "";
        }
    }
}
