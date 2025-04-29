package fileprocessor;

import fileprocessor.impl.TXTFileProcessor;
import fileprocessor.impl.XLSXFileProcessor;

/**
 * @author Aleksndar Zizovic
 */
public class FileProcessorFactory {
    public static FileProcessor getFileProcessor(String file) throws Exception {
        if (file.endsWith("txt")) {
            return new TXTFileProcessor();
        } else if (file.endsWith("xlsx")) {
            return new XLSXFileProcessor();
        } else {
            throw new Exception("Wrong extension");
        }
    }
}
