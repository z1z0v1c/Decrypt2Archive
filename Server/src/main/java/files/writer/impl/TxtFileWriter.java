package files.writer.impl;

import files.writer.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class TxtFileWriter implements FileWriter {
    public void writeText(String path, List<String> text) throws FileNotFoundException {
        try (var out = new PrintWriter(path)) {
            for (String word : text) {
                out.print(word);
            }
        }
    }
}
