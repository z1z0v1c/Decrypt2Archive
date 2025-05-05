package files.reader.impl;

import files.reader.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TxtFileReader implements FileReader {
    public List<String> readText(String path) throws FileNotFoundException {
        Scanner scanner;
        var file = new File(path);
        var text = new ArrayList<String>();

        try {
            scanner = new Scanner(file);

            while (scanner.hasNext()) {
                text.add(scanner.next());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TxtFileReader.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        return text;
    }
}
