package java_core_3.save;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static ArrayList<File> filesList = new ArrayList<>();

    public static void main(String[] args) {
        //Создание объектов для сериализации
        GameProgress gameProgress1 = new GameProgress(97, 150, 7, 777.7);
        GameProgress gameProgress2 = new GameProgress(79, 234, 9, 899.7);
        GameProgress gameProgress3 = new GameProgress(77, 24, 12, 1234.7);

        //Сериализация
        saveGame("C://Games/savegames/saveGP1.dat", gameProgress1);
        saveGame("C://Games/savegames/saveGP2.dat", gameProgress2);
        saveGame("C://Games/savegames/saveGP3.dat", gameProgress3);

        //Запаковка сериализированных файлов в архив
        zipFiles("C://Games/savegames/zip.zip", filesList);

        //Удаление всех файлов в папке кроме архива
        deleteFiles("C://Games/savegames");

    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            filesList.add(new File(path));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String pathZip, List<File> listObjects) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (File file : listObjects) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteFiles(String pathDir) {
        File dir = new File(pathDir);
        if (dir.isDirectory()) {
            try {
                for (File item : dir.listFiles()) {
                    if (item.isFile() && !item.getName().contains(".zip")) {
                        item.delete();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

