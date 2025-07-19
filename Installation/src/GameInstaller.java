import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameInstaller {
    public static void main(String[] args) {
        String basePath = "D:/Games/";
        // String basePath = "/Users/admin/Games/"; // Для MacOS

        StringBuilder log = new StringBuilder();

        File srcDir = createDirectory(basePath + "src", log);
        File resDir = createDirectory(basePath + "res", log);
        File savegamesDir = createDirectory(basePath + "savegames", log);
        File tempDir = createDirectory(basePath + "temp", log);

        File mainDir = createDirectory(srcDir.getPath() + "/main", log);
        File testDir = createDirectory(srcDir.getPath() + "/test", log);

        File mainJava = createFile(mainDir.getPath() + "/Main.java", log);
        File utilsJava = createFile(mainDir.getPath() + "/Utils.java", log);

        File drawablesDir = createDirectory(resDir.getPath() + "/drawables", log);
        File vectorsDir = createDirectory(resDir.getPath() + "/vectors", log);
        File iconsDir = createDirectory(resDir.getPath() + "/icons", log);

        File tempFile = createFile(tempDir.getPath() + "/temp.txt", log);

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(log.toString());
            System.out.println("Лог успешно записан в temp.txt");
        } catch (IOException e) {
            System.out.println("Ошибка при записи лога: " + e.getMessage());
        }
    }

    private static File createDirectory(String path, StringBuilder log) {
        File dir = new File(path);
        if (dir.mkdir()) {
            log.append("Директория ").append(path).append(" создана успешно\n");
        } else {
            log.append("Ошибка при создании директории ").append(path).append("\n");
        }
        return dir;
    }

    private static File createFile(String path, StringBuilder log) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                log.append("Файл ").append(path).append(" создан успешно\n");
            } else {
                log.append("Ошибка при создании файла ").append(path).append("\n");
            }
        } catch (IOException e) {
            log.append("Ошибка ввода-вывода при создании файла ")
                    .append(path).append(": ").append(e.getMessage()).append("\n");
        }
        return file;
    }
}