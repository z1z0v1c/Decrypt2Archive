/* 
 * Copyright (C) msg-global solutions 2018
 * msg-global.com
 * All rights reserved
 */
package encryption;

import db.DBConnection;
import java.util.ArrayList;
import java.util.List;
import fileprocessor.impl.TXTFileProcessor;
import fileprocessor.impl.XLSXFileProcessor;

/**
 *
 * @author Stefan Pantelic
 */
public class TTPEncryptionSuite {
    
//  public static void main(String[] args) {
//
//     DBConnection dbConnestion = new DBConnection();

//       String keyTXT = dbConnestion.selectKeyForTXT();        
//     String valueTXT = TXTFileProcessor.getTextFromFile();
//       
//      
//      String decryptedTXT = Encryptor.decrypt(keyTXT, valueTXT);
//       TXTFileProcessor.printToFile(decryptedTXT);
//       
//      System.out.println("Encripted .txt: " + valueTXT);
//        System.out.println("Value: " + valueTXT);
//        System.out.println("Decryption: " + decryptedTXT);
//       
//      String keyXLSX = dbConnestion.selectKeyForXLSX();        
//        List<String> listXLSX = XLSXFileProcessor.getTextFromFile("C:\\Users\\luciano\\Desktop\\TTP Level2\\Input\\ExternalInput.xlsx");
//       List<String> listXLSXDecrypted = new ArrayList<>();
//        
//        System.out.println("Encripted: " + listXLSX);
//      
//       for (String value : listXLSX) {
//            String decryptedXLSX = Encryptor.decrypt(keyXLSX, value);
//            listXLSXDecrypted.add(decryptedXLSX);
//       XLSXFileProcessor.printToFile("C:\\Users\\luciano\\Desktop\\TTP Level2 Solution\\Output\\ExternalOutput.xlsx",listXLSXDecrypted, 5, 3);
//        System.out.println("Value: " + listXLSX);
//        System.out.println("Decryption: " + listXLSXDecrypted);  
//   }
//}
}
