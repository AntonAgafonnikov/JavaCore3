package java_core_3.load;

import java_core_3.save.GameProgress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        // Распаковка архива
        openZip("C://Games/savegames/zip.zip", "C://Games/savegames");

        // Десериализация и вывод в консоль
        System.out.println(openProgress("C://Games/savegames/saveGP1.dat"));
        System.out.println(openProgress("C://Games/savegames/saveGP2.dat"));
        System.out.println(openProgress("C://Games/savegames/saveGP3.dat"));
    }

    private static void openZip(String pathZip, String pathUnzip) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла
                // распаковка
                StringBuilder pathUnzipSB = new StringBuilder(pathUnzip).append("/").append(name);
                FileOutputStream fout = new FileOutputStream(pathUnzipSB.toString());
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        // откроем входной поток для чтения файла
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // десериализуем объект и скастим его в класс
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
