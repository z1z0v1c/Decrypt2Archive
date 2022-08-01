package fileprocessor;

import java.util.List;

/**
 *
 * @author luciano
 */
public interface FileProcessor {
    List<String> getTextFromFile(String path);
    void printToFile(String path, List<String> text);
}
