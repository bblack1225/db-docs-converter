package com.blake;

import com.mysql.cj.xdevapi.Table;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
            List<String> data = List.of("1.", "ID", "NUMBER", "22", "Y", "Y", "", "PK", "");

            List<TableColData> tableColData = getMockData();
            for(TableColData dbTableData: tableColData){
                XWPFTableRow tableRowTwo = table.createRow();
                for(int i = 0; i < tableRowTwo.getTableCells().size(); i++){
                    // TODO 將資料以object帶過來
                    String value = getSpecPosData(dbTableData, i);
                    XWPFTableCell cell = tableRowTwo.getCell(i);
                    cell.setText(value);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    var paragraph = cell.getParagraphs().get(0);
                    paragraph.getRuns().get(0).setFontSize(10);
                    paragraph.getRuns().get(0).setBold(false);
                }
            }
//            TableColData tableColData = new TableColData();

//            data.forEach(val -> {
//                XWPFTableCell cell = tableRowTwo.createCell();
//                cell.setText(val);
//                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
//            });
            document.write(out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSpecPosData(TableColData tableColData, int i) {
        return switch (i) {
            case 0 -> tableColData.getColSeq();
            case 1 -> tableColData.getColName();
            case 2 -> tableColData.getColType();
            case 3 -> tableColData.getColLength();
            case 4 -> tableColData.getEnableNull();
            case 5 -> tableColData.getEnableUnique();
            case 6 -> tableColData.getColDefault();
            case 7 -> tableColData.getColComment();
            case 8 -> tableColData.getCheckRuleOrDomain();
            default -> throw new RuntimeException("Unexpected value: " + i);
        };
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

    public static List<TableColData> getMockData(){
        TableColData tableColData = new TableColData();
        tableColData.setColSeq("1.");
        tableColData.setColName("ID");
        tableColData.setColType("NUMBER");
        tableColData.setColLength("22");
        tableColData.setEnableNull("Y");
        tableColData.setEnableUnique("Y");
        tableColData.setColDefault("");
        tableColData.setColComment("PK");
        tableColData.setCheckRuleOrDomain("");
        TableColData tableColData2 = new TableColData();
        tableColData2.setColSeq("2.");
        tableColData2.setColName("IDENTITY_CODE");
        tableColData2.setColType("VARCHAR2");
        tableColData2.setColLength("100");
        tableColData2.setEnableNull("Y");
        tableColData2.setEnableUnique("Y");
        tableColData2.setColDefault("");
        tableColData2.setColComment("身分代號");
        tableColData2.setCheckRuleOrDomain("");
        TableColData tableColData3 = new TableColData();
        tableColData3.setColSeq("3.");
        tableColData3.setColName("USID");
        tableColData3.setColType("VARCHAR2");
        tableColData3.setColLength("20");
        tableColData3.setEnableNull("Y");
        tableColData3.setEnableUnique("Y");
        tableColData3.setColDefault("");
        tableColData3.setColComment("CRM帳號");
        tableColData3.setCheckRuleOrDomain("");
        TableColData tableColData4 = new TableColData();
        tableColData4.setColSeq("4.");
        tableColData4.setColName("USERNAME");
        tableColData4.setColType("VARCHAR2");
        tableColData4.setColLength("150");
        tableColData4.setEnableNull("N");
        tableColData4.setEnableUnique("Y");
        tableColData4.setColDefault("");
        tableColData4.setColComment("帳號");
        tableColData4.setCheckRuleOrDomain("");
        return List.of(tableColData, tableColData2, tableColData3, tableColData4);
    }
}
