package files.writer;

import files.writer.impl.TxtFileWriter;
import files.writer.impl.XlsxFileWriter;

import java.io.IOException;

public class FileWriterFactory {
    public static FileWriter createFileWriter(String file) throws IOException {
        if (file.endsWith("txt")) {
            return new TxtFileWriter();
        } else if (file.endsWith("xlsx")) {
            return new XlsxFileWriter();
        } else {
            throw new IOException("Wrong extension");
        }
    }
}
