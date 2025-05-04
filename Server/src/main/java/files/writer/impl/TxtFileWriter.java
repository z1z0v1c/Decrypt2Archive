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
        File output = new File(path);

        try {
            PrintWriter out = new PrintWriter(output);

            for (String string : text) {
                out.print(string);
            }

            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TxtFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
