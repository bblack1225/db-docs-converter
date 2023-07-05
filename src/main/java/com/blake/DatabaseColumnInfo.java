package com.blake;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseColumnInfo {

    private static final double A4_PAPER_WIDTH_IN_INCHES = 8.27;
    // Assume 1 inch margin
    private static final double MARGIN_IN_INCHES = 0.5;
    // Subtract margins
    private static final double TOTAL_CELL_WIDTH_IN_INCHES = A4_PAPER_WIDTH_IN_INCHES - 2 * MARGIN_IN_INCHES;
    private static final int TOTAL_COLUMN = 15;

//    private static final Map<String, Integer> TABLE_HEAD_CELL = Map.of("序號", 1, "欄位名稱", 3,
//            "資料型態", 2, "長度", 1, "NULL", 1, "Unique", 1,
//            "Default", 1, "資料名稱", 3, "Check Rule/Domain", 2);
    public static void main(String[] args) {
        try(XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream("資料庫結構NEW.docx")){
            XWPFTable table = document.createTable();
            table.setCellMargins(50, 0, 50, 0);
            XWPFTableRow tableRowOne  = table.getRow(0);

            double baseCellWidthInInches = TOTAL_CELL_WIDTH_IN_INCHES / TOTAL_COLUMN;
            // 先把第一格刪掉
            tableRowOne.removeCell(0);
            getHeaderAndColMap().forEach((title, col) -> {
                XWPFTableCell cell = tableRowOne.createCell();
                cell.setText(title);
                cell.setWidth(getWidthStrInTwips(baseCellWidthInInches * col));
                cell.setColor("CCCCCC");
                // 表格置中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                var paragraph = cell.getParagraphs().get(0);
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                paragraph.getRuns().get(0).setBold(true);
                paragraph.getRuns().get(0).setFontSize(10);


            });
            document.write(out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWidthStrInTwips(double widthInInches) {
        int widthInTwips = (int) (widthInInches * 1440);
        return String.valueOf(widthInTwips);
    }

    public static Map<String, Integer> getHeaderAndColMap(){
        Map<String, Integer> tableHeadCell = new LinkedHashMap<>();
        tableHeadCell.put("序號", 1);
        tableHeadCell.put("欄位名稱", 3);
        tableHeadCell.put("資料型態", 2);
        tableHeadCell.put("長度", 1);
        tableHeadCell.put("NULL", 1);
        tableHeadCell.put("Unique", 1);
        tableHeadCell.put("Default", 1);
        tableHeadCell.put("資料名稱", 3);
        tableHeadCell.put("Check Rule/Domain", 2);
        return tableHeadCell;
    }
}
