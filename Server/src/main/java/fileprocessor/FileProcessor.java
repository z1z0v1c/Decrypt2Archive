package fileprocessor;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Aleksndar Zizovic
 */
public interface FileProcessor {
    List<String> getTextFromFile(String path) throws FileNotFoundException;
    void printToFile(String path, List<String> text);
}
