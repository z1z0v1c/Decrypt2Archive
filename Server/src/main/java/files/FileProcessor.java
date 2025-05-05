package files;

import files.encryptor.FileEncryptor;
import files.reader.FileReader;
import files.reader.factory.FileReaderFactory;
import files.writer.FileWriter;
import files.writer.factory.FileWriterFactory;
import files.zipper.FileZipper;

import java.io.IOException;
import java.util.List;

public class FileProcessor {
    private FileZipper fileZipper;
    private FileEncryptor fileEncryptor;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public FileProcessor(String inputFile, String outputFile) throws IOException {
        fileZipper = new FileZipper();
        fileEncryptor = new FileEncryptor();
        fileReader = FileReaderFactory.createFileReader(inputFile);
        fileWriter = FileWriterFactory.createFileWriter(outputFile);
    }

    public void process(String inputFile, String outputFile, String key) throws IOException {
        List<String> text = fileReader.readText(inputFile);

        List<String> decryptedText = fileEncryptor.decrypt(key, text);

        fileWriter.writeText(outputFile, decryptedText);

        fileZipper.zipDirectory(outputFile);
    }
}
