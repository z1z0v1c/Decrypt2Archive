package file.writer;

import java.util.List;

public interface FileWriter {
    void writeText(String path, List<String> text);
}
