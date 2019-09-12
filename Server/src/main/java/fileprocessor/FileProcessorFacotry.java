/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileprocessor;

import fileprocessor.impl.TXTFileProcessor;
import fileprocessor.impl.XLSXFileProcessor;

/**
 *
 * @author luciano
 */
public class FileProcessorFacotry {

   public static FileProcessor getFileProcessor(String file) throws Exception {
        if (file.endsWith("txt")) {
            return new TXTFileProcessor();
        } else if (file.endsWith("xlsx")) {
            return new XLSXFileProcessor();
        }else{
            throw new Exception("Wrong extension");
        }
    }
}
