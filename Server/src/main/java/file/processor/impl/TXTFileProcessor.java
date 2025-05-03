package file.processor.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import file.processor.FileProcessor;

/**
 * @author Aleksndar Zizovic
 */
public class TXTFileProcessor implements FileProcessor {
    public List<String> readText(String path) throws FileNotFoundException {
        Scanner in;
        File source = new File(path);
        List<String> text = new ArrayList<>();

        try {
            in = new Scanner(source);

            while (in.hasNext()) {
                text.add(in.next());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TXTFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        return text;
    }

    public void writeText(String path, List<String> text) {
        File output = new File(path);

        try {
            PrintWriter out = new PrintWriter(output);

            for (String string : text) {
                out.print(string);
            }

            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TXTFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
