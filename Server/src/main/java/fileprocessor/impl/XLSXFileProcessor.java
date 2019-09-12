/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileprocessor.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import fileprocessor.FileProcessor;

/**
 *
 * @author luciano
 */
public class XLSXFileProcessor implements FileProcessor {

    public List<String> getTextFromFile(String path) {
        File source = new File(path);
        List<String> text = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(source);
            DataFormatter dataFormatter = new DataFormatter();
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        text.add(dataFormatter.formatCellValue(cell));
                    }
                    System.out.println();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(XLSXFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(XLSXFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }

    public void printToFile(String path, List<String> text) {
        File output = new File(path);
        FileOutputStream fileOut;
        int columns = 3;
        try {
            fileOut = new FileOutputStream(output);

            Workbook workbook = new XSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Sheet 1");

            int index = 0;
//            for (int i = 0; i < rows; i++) {
            int rowNumber = 0;
            while (index < text.size()) {
                Row row = sheet.createRow(rowNumber);
                for (int j = 0; j < columns; j++) {
                    row.createCell(j).setCellValue(text.get(index));
                    index++;
                    if (index % columns == 0) {
                        rowNumber++;
                    }
                }
            }

            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XLSXFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XLSXFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
