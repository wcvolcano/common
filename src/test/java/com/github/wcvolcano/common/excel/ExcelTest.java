package com.github.wcvolcano.common.excel;

import com.github.wcvolcano.common.file.excel.ExcelHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by canwen on 2016/11/30.
 */
public class ExcelTest {
    public static void main(String[] args) throws IOException {
        heightTest();
    }

    private static void heightTest() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        int rowEnd = 10;
        for(int row = 0; row < rowEnd ; ++row) {
            ExcelHelper.getCell(sheet, row, 0).setCellValue(row);
        }

        XSSFRow row = sheet.getRow(2);
        row.setHeightInPoints(10 * sheet.getDefaultRowHeightInPoints());

        workbook.write(new FileOutputStream(new File("data/excel/height.xlsx")));
        workbook.close();
    }
}
