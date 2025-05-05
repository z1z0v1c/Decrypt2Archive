package files.reader.impl;

import files.reader.FileReader;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsxFileReader implements FileReader {
    public List<String> readText(String path) throws IOException {
        var text = new ArrayList<String>();

        try (var workbook = WorkbookFactory.create(new File(path))) {
            var dataFormatter = new DataFormatter();

            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        text.add(dataFormatter.formatCellValue(cell));
                    }

                    System.out.println();
                }
            }
        }

        return text;
    }
}
