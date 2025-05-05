package files.writer;

import java.io.IOException;
import java.util.List;

/// @author Aleksandar Zizovic
public interface FileWriter {
    void writeText(String path, List<String> text) throws IOException;
}
