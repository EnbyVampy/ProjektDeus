package us.projektdeus.currentlyunused;

import us.projektdeus.client.test.LogHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileManager {
    private static final Logger logger = LogHandler.getLogger(FileManager.class);
    public static void main(String[] args) {
        LogHandler.setLoggingLevel(FileManager.class, 1);
    }

    public void checkFileExists(String fileName) {
        logger.info("Checking files...");
        if(Files.exists(Paths.get(fileName))){
            logger.info(fileName+" exists.");
        }else {
            logger.info(fileName+" does not exist.");
            //createFile(fileName);
        }
    }

    public void checkDirExists(String fileName) {
        logger.info("Checking directories...");
        if(Files.exists(Paths.get(fileName))){
            logger.info(fileName+" exists.");
        }else {
            logger.info(fileName+" does not exist.");
            //createDir(fileName);
        }
    }

    public void createFile(String fileName) {
        logger.info("Creating "+fileName);
            try {
                Files.createFile(Paths.get(fileName));
                logger.info(fileName + " created..");
            } catch (Exception e) {
                logger.info("Error creating file: " + fileName + ". " + e.getMessage());
            }
    }

    public void createDir(String fileName){
        logger.info("Creating "+fileName);
        try{
            Files.createDirectories(Paths.get(fileName));
            logger.info(fileName+" created.");
        }catch(Exception e){
            logger.info("Error creating directory.");
        }
    }

    // Retrieve all files recursively from a directory, optionally filtered by extension.
    public List<Path> getFilesRecursively(String dirPath) {
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(dirPath))) {
            paths.filter(Files::isRegularFile).forEach(files::add);
        } catch (IOException e) {
            logger.severe("Error while accessing directory: " + dirPath + ". Error: " + e.getMessage());
        }
        return files;
    }

    public boolean isImageFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") ||
                fileName.endsWith(".jpeg") || fileName.endsWith(".gif");
    }



}