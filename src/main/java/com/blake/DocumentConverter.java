package com.blake;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class DocumentConverter {
    public static void main(String[] args) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText("Hello world");
        xwpfRun.addBreak();
        XWPFTable table = document.createTable();
        XWPFTableRow tableRowOne  = table.getRow(0);
        double paperWidthInInches = 8.27; // A4 paper width
        double marginInInches = 1; // Assume 1 inch margin
        double totalCellWidthInInches = paperWidthInInches - 2 * marginInInches; // Subtract margins

// Divide by the number of cells
        double cellWidthInInches = totalCellWidthInInches / 3;

// Convert to twips
        int cellWidthInTwips = (int) (cellWidthInInches * 1440);
        XWPFTableCell cell = tableRowOne.getCell(0);
        cell.setText("表名稱");
        cell.setWidth(String.valueOf(cellWidthInTwips));
        cell.setColor("FF0000");
        XWPFTableCell cell2 = tableRowOne.addNewTableCell();
        cell2.setText("欄位名稱");
        cell2.setWidth(String.valueOf(cellWidthInTwips));
        XWPFTableCell cell3 = tableRowOne.addNewTableCell();
        cell3.setText("欄位型態");
        cell3.setWidth(String.valueOf(cellWidthInTwips));
        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText("users");
        tableRowTwo.getCell(1).setText("id");
        tableRowTwo.getCell(2).setText("int");

        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("users");
        tableRowThree.getCell(1).setText("name");
        tableRowThree.getCell(2).setText("varchar");

        try (FileOutputStream out = new FileOutputStream("資料庫結構.docx")) {
            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
