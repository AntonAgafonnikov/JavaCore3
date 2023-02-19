package java_core_3.setup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.Date;

public class Main {

    private static StringBuilder tempSB = new StringBuilder();
    private static boolean flagError = false;

    public static void main(String[] args) {
        //Инициализация главного каталога
        File dirGames = new File("C://Games");

        //Инициализация и создание папок (src, res, savegames, temp) в главном каталоге
        File dirSrc = new File(dirGames, "src");
        File dirRes = new File(dirGames, "res");
        File dirSaveGames = new File(dirGames, "savegames");
        File dirTemp = new File(dirGames, "temp");
        createDirectory(dirSrc);
        createDirectory(dirRes);
        createDirectory(dirSaveGames);
        createDirectory(dirTemp);

        //Инициализация и создание папок (main, test) в подкаталоге src
        File dirMain = new File(dirSrc, "main");
        File dirTest = new File(dirSrc, "test");
        createDirectory(dirMain);
        createDirectory(dirTest);

        //Инициализация и создание файлов (Main.java, Test.java) в подкаталоге main
        File fileMain = new File(dirMain, "Main.java");
        File fileTest = new File(dirMain, "Test.java");
        createFile(fileMain);
        createFile(fileTest);

        //Инициализация и создание папок (drawables, vectors, icons) в подкаталоге res
        File dirDrawables = new File(dirRes, "drawables");
        File dirVectors = new File(dirRes, "vectors");
        File dirIcons = new File(dirRes, "icons");
        createDirectory(dirDrawables);
        createDirectory(dirVectors);
        createDirectory(dirIcons);

        //Инициализация и создание файла temp.txt в подкаталоге temp
        File fileTemp = new File(dirTemp, "temp.txt");
        createFile(fileTemp);
        logEntry(fileTemp);

        if (flagError) {
            System.out.println("Программа завершена с ошибками - см. лог-файл -> " + fileTemp.getName());
        } else {
            System.out.println("Программа завершена без ошибок");
        }
    }

    private static void createDirectory(File dir) {
        tempSB.append(new Date())
                .append(": Создание папки - ").append(dir.getName())
                .append(" - по адресу -> ").append(dir.getParent());
        if (dir.mkdir()) {
            tempSB.append(" - [ВЫПОЛНЕНО]\r\n");
        } else {
            flagError = true;
            tempSB.append(" - [ОШИБКА]\r\n");
        }
    }

    private static void createFile(File file) {
        tempSB.append(new Date())
                .append(": Создание файла - ").append(file.getName())
                .append(" - по адресу -> ").append(file.getParent());
        try {
            if (file.createNewFile()) {
                tempSB.append(" - [ВЫПОЛНЕНО]\r\n");
            } else {
                flagError = true;
                tempSB.append(" - [ОШИБКА]\r\n");
            }
        } catch (IOException ex) {
            flagError = true;
            tempSB.append(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    private static void logEntry(File fileTemp) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {
            tempSB.append(new Date())
                    .append(": Запись логов в файл - ").append(fileTemp.getName())
                    .append(" - по адресу -> ").append(fileTemp.getParent());
            bw.write(tempSB.toString());
        } catch (IOException ex) {
            flagError = true;
            System.out.println(ex.getMessage());
            tempSB.append(ex.getMessage());
        }
    }
}