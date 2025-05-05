package files.writer.impl;

import files.writer.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TxtFileWriter implements FileWriter {
    public void writeText(String path, List<String> text) {
        var output = new File(path);

        try {
            try (var out = new PrintWriter(output)) {
                for (String string : text) {
                    out.print(string);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TxtFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
