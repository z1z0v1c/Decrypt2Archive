package files.reader.impl;

import files.reader.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/// @author Aleksandar Zizovic
public class TxtFileReader implements FileReader {
    public List<String> readText(String path) throws FileNotFoundException {
        var text = new ArrayList<String>();

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                text.add(scanner.next());
            }
        }

        return text;
    }
}
