package pechpal;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {
    private  String[] necessaryFiles = {
            "ongevallen",
            "wegvakken",
            "hectopunten",
            "puntlocaties",
            "Referentie",
            "partijen"
    };

    public UnZip(Path pthTempDir, String location) throws IOException {
        this.unzipFiles(location, pthTempDir);
    }

    private void unzipFiles(String file, Path path) throws IOException {
        String strTempDir = path.toString();
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                saveToTempFolder(strTempDir, entry, zipInputStream);
                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dit bestand bestaat niet");
            alert.show();
        }
    }

    private void saveToTempFolder(String tempDir, ZipEntry ze, ZipInputStream zis) throws IOException {
        Path filePath = Paths.get(tempDir, ze.getName());
        if (!ze.isDirectory()
                && ze.getName().endsWith(".txt")
                && containsNecessaryFiles(ze)) {
            unzipFiles(zis, filePath);
        } else if (ze.isDirectory()) {
            Files.createDirectories(filePath);
        }
    }

    private boolean containsNecessaryFiles(ZipEntry entry) {
        return Arrays.stream(this.necessaryFiles).anyMatch(entry.getName()::contains);
    }

    private static void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            byte[] bytesIn = new byte[1024];
            int read;
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
