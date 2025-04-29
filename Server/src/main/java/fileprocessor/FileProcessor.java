package fileprocessor;

import java.util.List;

/**
 *
 * @author Aleksndar Zizovic
 */
public interface FileProcessor {
    List<String> getTextFromFile(String path);
    void printToFile(String path, List<String> text);
}
