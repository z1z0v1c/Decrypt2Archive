package files.writer;

import java.io.IOException;
import java.util.List;

public interface FileWriter {
    void writeText(String path, List<String> text) throws IOException;
}
