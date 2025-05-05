package files.zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/// @author Aleksndar Zizovic
public class FileZipper {
    public void zipDirectory(String path) throws IOException {
        var original = new File(path).getParentFile();

        try (var fileOutput = new FileOutputStream(original.getParentFile().getPath() + "\\Output.zip");
             var zipped = new ZipOutputStream(fileOutput)) {
            zipFile(original, original.getName(), zipped);
        }
    }

    private void zipFile(File original, String fileName, ZipOutputStream zipped) throws IOException {
        if (original.isHidden()) {
            return;
        }

        if (original.isDirectory()) {
            if (fileName.endsWith("\\")) {
                zipped.putNextEntry(new ZipEntry(fileName));
                zipped.closeEntry();
            } else {
                zipped.putNextEntry(new ZipEntry(fileName + "\\"));
                zipped.closeEntry();
            }

            File[] children = original.listFiles();

            if (children == null) throw new IOException("Directory is empty.");

            for (File childFile : children) {
                zipFile(childFile, fileName + "\\" + childFile.getName(), zipped);
            }

            return;
        }

        try (var inputStream = new FileInputStream(original)) {
             var zipEntry = new ZipEntry(fileName);

            zipped.putNextEntry(zipEntry);

            int length;
            byte[] bytes = new byte[1024];

            while ((length = inputStream.read(bytes)) >= 0) {
                zipped.write(bytes, 0, length);
            }
        }
    }
}
