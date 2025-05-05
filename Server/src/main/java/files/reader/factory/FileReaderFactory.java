package files.reader.factory;

import files.reader.FileReader;
import files.reader.impl.TxtFileReader;
import files.reader.impl.XlsxFileReader;

import java.io.IOException;

/// @author Aleksandar Zizovic
public class FileReaderFactory {
    public static FileReader createFileReader(String file) throws IOException {
        if (file.endsWith(".txt")) {
            return new TxtFileReader();
        } else if (file.endsWith(".xlsx")) {
            return new XlsxFileReader();
        } else {
            throw new IOException("Incompatible file extension.");
        }
    }
}
