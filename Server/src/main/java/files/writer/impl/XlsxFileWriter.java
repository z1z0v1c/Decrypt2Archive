package files.writer.impl;

import files.writer.FileWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XlsxFileWriter implements FileWriter {
    public void writeText(String path, List<String> text) {
        int columns = 3;
        FileOutputStream fileOut;
        File output = new File(path);

        try {
            fileOut = new FileOutputStream(output);
            Workbook workbook = new XSSFWorkbook();

            workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Sheet 1");

            int index = 0;
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
            Logger.getLogger(XlsxFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XlsxFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
