package file.reader;

import file.reader.impl.TxtFileReader;
import file.reader.impl.XlsxFileReader;

import java.io.IOException;

public class FileReaderFactory {
    public static FileReader createFileReader(String file) throws IOException {
        if (file.endsWith("txt")) {
            return new TxtFileReader();
        } else if (file.endsWith("xlsx")) {
            return new XlsxFileReader();
        } else {
            throw new IOException("Wrong extension");
        }
    }
}
