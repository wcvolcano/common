package com.github.wcvolcano.common.file.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by canwen on 2016/8/27.
 */
public class ExcelHelper {
    public static XSSFCell getCell(XSSFSheet sheet, int rowPos, int col) {
        XSSFRow row = sheet.getRow(rowPos);
        if (row == null) {
            row = sheet.createRow(rowPos);
        }

        XSSFCell cell = row.getCell(col);
        if (cell == null) {
            cell = row.createCell(col);
        }

        return cell;
    }

    public static XSSFCell getCell(XSSFSheet sheet, int rowPos, int col, CellStyle cellStyle) {
        XSSFCell cell = getCell(sheet, rowPos, col);
        cell.setCellStyle(cellStyle);
        return cell;
    }


    public static XSSFWorkbook getInitExcel(String path) throws IOException {
        return new XSSFWorkbook(new FileInputStream(path));
    }

    public static void setWrapText(Cell cell, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        cell.setCellStyle(style);
    }

    public static void setMultiLen(Sheet sheet, int column ,int factor) {
        sheet.setColumnWidth(column, factor*sheet.getColumnWidth(column));
    }

    public static void setMultiHeight(Sheet sheet, int row, int factor) {
        sheet.getRow(row).setHeightInPoints(factor*sheet.getDefaultRowHeightInPoints());
    }
}
