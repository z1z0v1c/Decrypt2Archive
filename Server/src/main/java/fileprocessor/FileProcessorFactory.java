package fileprocessor;

import fileprocessor.impl.TXTFileProcessor;
import fileprocessor.impl.XLSXFileProcessor;

import java.io.IOException;

/**
 * @author Aleksndar Zizovic
 */
public class FileProcessorFactory {
    public static FileProcessor getFileProcessor(String file) throws IOException {
        if (file.endsWith("txt")) {
            return new TXTFileProcessor();
        } else if (file.endsWith("xlsx")) {
            return new XLSXFileProcessor();
        } else {
            throw new IOException("Wrong extension");
        }
    }
}
