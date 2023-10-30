import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FolderFlattenerLogic  {
    private static Path originalPath;
    private static boolean exitStatus;
    
    public static boolean getExitStatus() {
        return exitStatus;
    }

    public static void crushFolder(File selectedDirectory) throws IOException {
        exitStatus = true;
        originalPath = selectedDirectory.toPath();
        crushFolderHelp(selectedDirectory);   
    }

    private static void crushFolderHelp(File selectedDirectory) throws IOException{
        File[] files = selectedDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    crushFolderHelp(file);
                    try {
                        Files.delete(file.toPath());
                    }
                    catch (IOException e) {
                        exitStatus = false;
                    }
                }
                else {
                    Path sourceFilePath = file.getParentFile().toPath();
                    if (!sourceFilePath.equals(originalPath)) {
                        try {
                            Files.move(file.toPath(), new File(originalPath.toFile(), file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        catch (IOException e1) {
                            exitStatus = false;
                        }
                    }
                }
            }
        }
    }
}
