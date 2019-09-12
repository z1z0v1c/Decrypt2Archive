/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileprocessor;

import java.util.List;

/**
 *
 * @author luciano
 */
public interface FileProcessor {
    List<String> getTextFromFile(String path);
    void printToFile(String path, List<String> text);
}
