package files.reader;

import java.io.IOException;
import java.util.List;

/// @author Aleksandar Zizovic
public interface FileReader {
    List<String> readText(String path) throws IOException;
}
