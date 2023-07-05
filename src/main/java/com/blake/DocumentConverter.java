package com.blake;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class DocumentConverter {
    private static final double A4_PAPER_WIDTH_IN_INCHES = 8.27;
    // Assume 1 inch margin
    private static final double MARGIN_IN_INCHES = 1;
    // Subtract margins
    private static final double TOTAL_CELL_WIDTH_IN_INCHES = A4_PAPER_WIDTH_IN_INCHES - 2 * MARGIN_IN_INCHES;
    public static void main(String[] args) {
        XWPFDocument document = new XWPFDocument();
//        XWPFParagraph paragraph = document.createParagraph();
//        XWPFRun xwpfRun = paragraph.createRun();
//        xwpfRun.setText("Hello world");
//        xwpfRun.addBreak();
        XWPFTable table = document.createTable();
        table.setCellMargins(50, 0, 50, 0);
        // row1要使用getRow(0)建立
        XWPFTableRow tableRowOne  = table.getRow(0);

// Divide by the number of cells
//        double cellWidthInInches = TOTAL_CELL_WIDTH_IN_INCHES / 3;
        double baseCellWidthInInches = TOTAL_CELL_WIDTH_IN_INCHES / 12;
        double cellOneTitleWidthInInches = baseCellWidthInInches * 2;
        double cellTwoValueWidthInInches =baseCellWidthInInches * 5;
        double lastCellWidthInInches =  baseCellWidthInInches * 3;

// Convert to twips
        tableRowOne.removeCell(0);
        XWPFTableCell cell = tableRowOne.addNewTableCell();
        cell.setWidth(getWidthStrInTwips(cellOneTitleWidthInInches));
        cell.setText("檔案名稱");
        cell.setColor("CCCCCC");
        cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        cell.getParagraphs().get(0).getRuns().get(0).setBold(true);
        XWPFTableCell cell2 = tableRowOne.addNewTableCell();
//        cell2.setText("欄位名稱");
        cell2.setWidth(getWidthStrInTwips(cellTwoValueWidthInInches));
        XWPFTableCell cell3 = tableRowOne.addNewTableCell();
        cell3.setText("版本");
        cell3.setColor("CCCCCC");
        cell3.setWidth(getWidthStrInTwips(cellOneTitleWidthInInches));
        cell3.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        cell3.getParagraphs().get(0).getRuns().get(0).setBold(true);
        XWPFTableCell cell4 = tableRowOne.addNewTableCell();
        cell4.setWidth(getWidthStrInTwips(lastCellWidthInInches));
//        XWPFTableRow tableRowTwo = table.createRow();
//        tableRowTwo.getCell(0).setText("users");
//        tableRowTwo.getCell(1).setText("id");
//        tableRowTwo.getCell(2).setText("int");
//
//        XWPFTableRow tableRowThree = table.createRow();
//        tableRowThree.getCell(0).setText("users");
//        tableRowThree.getCell(1).setText("name");
//        tableRowThree.getCell(2).setText("varchar");

        try (FileOutputStream out = new FileOutputStream("資料庫結構.docx")) {
            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWidthStrInTwips(double widthInInches) {
        int widthInTwips = (int) (widthInInches * 1440);
        return String.valueOf(widthInTwips);
    }

}
