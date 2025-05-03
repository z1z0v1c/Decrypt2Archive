package file.reader.impl;

import file.reader.FileReader;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XlsxFileReader implements FileReader {
    public List<String> readText(String path) {
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
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(XlsxFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return text;
    }
}
