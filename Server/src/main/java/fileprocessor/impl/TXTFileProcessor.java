/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileprocessor.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import fileprocessor.FileProcessor;

/**
 *
 * @author luciano
 */
public class TXTFileProcessor implements FileProcessor {

    public List<String> getTextFromFile(String path) {
        File source = new File(path);
        Scanner in;
        List<String> text = new ArrayList<>();
        try {
            in = new Scanner(source);
            while (in.hasNext()) {
                text.add(in.next());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TXTFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }

    public void printToFile(String path, List<String> text) {
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
