package file.reader;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileReader {
    List<String> readText(String path) throws FileNotFoundException;
}
