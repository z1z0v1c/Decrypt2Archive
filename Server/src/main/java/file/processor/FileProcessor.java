package file.processor;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Aleksndar Zizovic
 */
public interface FileProcessor {
    List<String> readText(String path) throws FileNotFoundException;
    void writeText(String path, List<String> text);
}
