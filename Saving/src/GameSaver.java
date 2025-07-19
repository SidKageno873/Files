import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameSaver {
    public static void main(String[] args) {
        String savegamesPath = "D:/Games/savegames/";
        // String savegamesPath = "/Users/admin/Games/savegames/"; // Для MacOS

        GameProgress progress1 = new GameProgress(100, 3, 5, 125.5);
        GameProgress progress2 = new GameProgress(85, 5, 10, 345.2);
        GameProgress progress3 = new GameProgress(50, 2, 3, 67.8);

        List<String> savedFiles = new ArrayList<>();
        savedFiles.add(savegamesPath + "save1.dat");
        savedFiles.add(savegamesPath + "save2.dat");
        savedFiles.add(savegamesPath + "save3.dat");

        saveGame(savedFiles.get(0), progress1);
        saveGame(savedFiles.get(1), progress2);
        saveGame(savedFiles.get(2), progress3);

        String zipPath = savegamesPath + "saves.zip";
        zipFiles(zipPath, savedFiles);

        deleteFiles(savedFiles);
    }

    public static void saveGame(String filePath, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
            System.out.println("Сохранение успешно записано в " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : filesToZip) {
                File fileToZip = new File(filePath);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                    System.out.println("Файл " + filePath + " добавлен в архив");
                } catch (IOException e) {
                    System.out.println("Ошибка при добавлении файла в архив: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании архива: " + e.getMessage());
        }
    }

    public static void deleteFiles(List<String> filesToDelete) {
        for (String filePath : filesToDelete) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Файл " + filePath + " удален");
            } else {
                System.out.println("Не удалось удалить файл " + filePath);
            }
        }
    }
}

class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}