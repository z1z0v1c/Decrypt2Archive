package files.writer.impl;

import files.writer.FileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TxtFileWriter implements FileWriter {
    public void writeText(String filePath, List<String> text) throws IOException {
        Path path = Paths.get(filePath);

        if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }

        try (var out = new PrintWriter(path.toFile())) {
            for (String word : text) {
                out.print(word);
            }
        }
    }
}
