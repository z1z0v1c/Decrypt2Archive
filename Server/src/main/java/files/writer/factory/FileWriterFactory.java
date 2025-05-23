package files.writer.factory;

import files.writer.FileWriter;
import files.writer.impl.TxtFileWriter;
import files.writer.impl.XlsxFileWriter;

import java.io.IOException;

/// @author Aleksandar Zizovic
public class FileWriterFactory {
    public static FileWriter createFileWriter(String file) throws IOException {
        if (file.endsWith(".txt")) {
            return new TxtFileWriter();
        } else if (file.endsWith(".xlsx")) {
            return new XlsxFileWriter();
        } else {
            throw new IOException("Incompatible file extension.");
        }
    }
}
