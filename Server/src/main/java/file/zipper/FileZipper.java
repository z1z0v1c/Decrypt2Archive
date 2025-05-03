package file.zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Aleksndar Zizovic
 */
public class FileZipper {
    public void zipDirectory(String path) throws IOException {
        File fileToZip = new File(path).getParentFile();

        try (FileOutputStream fos = new FileOutputStream(fileToZip.getParentFile().getPath() + "\\Output.zip"); 
                ZipOutputStream zipOut = new ZipOutputStream(fos)) {                      
            zipFile(fileToZip, fileToZip.getName(), zipOut);
        }
    }
 
    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }

        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("\\")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "\\"));
                zipOut.closeEntry();
            }

            File[] children = fileToZip.listFiles();
            assert children != null;

            for (File childFile : children) {
                zipFile(childFile, fileName + "\\" + childFile.getName(), zipOut);
            }

            return;
        }

        FileInputStream inputStream = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);

        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;

        while ((length = inputStream.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        inputStream.close();
    }
}
