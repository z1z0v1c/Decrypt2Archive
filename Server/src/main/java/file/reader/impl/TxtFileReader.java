package file.reader.impl;

import file.reader.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TxtFileReader implements FileReader {
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
            Logger.getLogger(TxtFileReader.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        return text;
    }
}
