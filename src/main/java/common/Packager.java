package common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Packager {

    //private static final String DIR_NAME = "/Users/jetblack/workspace/java/gen/src/main/java";
    //private static final String DEST_DIR_NAME = "/Users/jetblack/workspace/java/gen/target/deploy/";
    private static final String DIR_NAME = "C:\\myfolder\\workspace\\my\\gen\\src\\main\\java";
    private static final String DEST_DIR_NAME = "C:\\myfolder\\workspace\\my\\gen\\target\\deploy\\";


    private static final List<String> excluded = new ArrayList<String>();

    static {
        excluded.add("ActionType.java");
        excluded.add("Bonus.java");
        excluded.add("BonusType.java");
        excluded.add("CellType.java");
        excluded.add("Direction.java");
        excluded.add("Game.java");
        excluded.add("Move.java");
        excluded.add("Player.java");
        excluded.add("PlayerContext.java");
        excluded.add("Trooper.java");
        excluded.add("TrooperStance.java");
        excluded.add("TrooperType.java");
        excluded.add("Unit.java");
        excluded.add("World.java");
        excluded.add("Runner.java");
        excluded.add("RemoteProcessClient.java");
        excluded.add("Strategy.java");
        excluded.add("Packager.java");
    }

    public static void main(String[] args) throws IOException {
        List<File> files = new ArrayList<File>();
        fillFiles(files, new File(DIR_NAME));
        copyFiles(files);
    }

    private static void copyFiles(List<File> files) throws IOException {
        for (File file : files) {
            if (!excluded.contains(file.getName())) {
                processFile(file);
            }
        }

    }

    private static void processFile(File file) throws IOException {
        File destFile = new File(DEST_DIR_NAME + file.getName());
        destFile.createNewFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
        String lineToRemove = "import model";
        String lineNotRemove = "import java";
        String packLine = "package";
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith("package crossing")) {
                return;
            }
            if (currentLine.startsWith(packLine) || currentLine.startsWith("import"))
                if (!currentLine.startsWith(lineNotRemove) && !currentLine.startsWith(lineToRemove)) {
                    continue;
                }
            writer.write(currentLine + "\n");
        }
        reader.close();
        writer.close();
    }

    private static void fillFiles(List<File> files, File file) {
        if (file.isDirectory()) {
            File[] sub = file.listFiles();
            for (File subFile : sub) {
                fillFiles(files, subFile);
            }
        } else {
            files.add(file);
        }
    }

}
