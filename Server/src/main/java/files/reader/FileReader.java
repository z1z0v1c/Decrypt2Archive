package files.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<String> readText(String path) throws IOException;
}
